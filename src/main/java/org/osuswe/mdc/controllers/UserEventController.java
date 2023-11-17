package org.osuswe.mdc.controllers;

import lombok.RequiredArgsConstructor;
import org.osuswe.mdc.dto.*;
import org.osuswe.mdc.model.*;
import org.osuswe.mdc.services.EventService;
import org.osuswe.mdc.services.EventDetailsService;
import org.osuswe.mdc.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/resource/user_event")
@RequiredArgsConstructor
public class UserEventController {
    private final UserService userService;
    private final EventService eventService;
    private final EventDetailsService eventDetailsService;

    @PostMapping
    public ResponseEntity<GeneralResponse> updateUserEventStatus(@RequestHeader("Authorization") String bearerToken,
                                                                 @RequestBody UpdateEventDetailsRequest request) {
        User user = userService.getUserFromBearerToken(bearerToken);
        request.setUserId(user.getId());

        eventDetailsService.updateEventDetails(request);
        GeneralResponse resp = new GeneralResponse(HttpStatus.OK.value(), "");
        return ResponseEntity.ok(resp);
    }


    @PostMapping("/checkin")
    public ResponseEntity<GeneralResponse> checkin(@RequestHeader("Authorization") String bearerToken,
                                                   @RequestBody CheckinRequest request) {
        User user = userService.getUserFromBearerToken(bearerToken);

        eventDetailsService.checkIn(user.getId(), request.getEventId(),
                request.getCheckinCode());
        GeneralResponse resp = new GeneralResponse(HttpStatus.OK.value(), "");
        return ResponseEntity.ok(resp);
    }

    @RequestMapping(value = "/{event_id}", method = RequestMethod.GET)
    public ResponseEntity<GetEventDetailsResponse> getUserEvent(@RequestHeader("Authorization") String bearerToken,
                                                                @PathVariable("event_id") int eventId) {
        User user = userService.getUserFromBearerToken(bearerToken);
        Event event = eventService.getEvent(eventId);
        EventDetail detail = new EventDetail();
        DateFormat df = new SimpleDateFormat("MMMM dd, yyyy HH:mm");

        detail.setUserId(user.getId());
        detail.setEventId(event.getId());
        detail.setTitle(event.getTitle());
        detail.setDetails(event.getDetails());
        detail.setDate(df.format(event.getDate()));
        detail.setStatus(EventStatus.NOT_DECIDED.ordinal());
        detail.setShowUp(0);
        detail.setComments("");
        detail.setLocation(event.getLocation());
        Optional<User> organizer = Optional.empty();
        if (event.getOrganizer() != null) {
            organizer = userService.getUser(event.getOrganizer());

        }
        organizer.ifPresent(value -> detail.setOrganizer(value.getFirst_name() + " " + value.getLast_name()
        ));
        detail.setEventCode(event.getEvent_code());

        var ue = eventDetailsService.getEventDetails(user.getId(), eventId);
        if (ue.isPresent()) {
            detail.setStatus(ue.get().getStatus());
            detail.setShowUp(ue.get().getShow_up());
            detail.setComments(ue.get().getComments());
        }

        GetEventDetailsResponse resp = new GetEventDetailsResponse(HttpStatus.OK.value(), "");
        resp.setEventDetails(detail);
        return ResponseEntity.ok(resp);
    }

    @RequestMapping(value = "/attendees/{event_id}", method = RequestMethod.GET)
    public ResponseEntity<GetAttendeesResponse> getAttendees(@PathVariable("event_id") int eventId) {
        List<EventAttendee> attendees = eventDetailsService.getAttendees(eventId);
        GetAttendeesResponse resp = new GetAttendeesResponse(HttpStatus.OK.value(), "");
        resp.setAttendees(attendees);
        for (var e : attendees) {
            if (e.getStatus() == EventStatus.NOT_DECIDED.ordinal()) {
                resp.setNotDecided(resp.getNotDecided() + 1);
            } else if (e.getStatus() == EventStatus.ACCEPT.ordinal()) {
                resp.setAccept(resp.getAccept() + 1);
            } else if (e.getStatus() == EventStatus.REJECT.ordinal()) {
                resp.setReject(resp.getReject() + 1);
            }
            if (e.getShowUp() == 1) {
                resp.setShowUp(resp.getShowUp() + 1);
            }
        }

        return ResponseEntity.ok(resp);
    }
}
