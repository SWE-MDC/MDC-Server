package org.osuswe.mdc.controllers;

import lombok.RequiredArgsConstructor;
import org.osuswe.mdc.dto.GetAttendeesResponse;
import org.osuswe.mdc.dto.UpdateUserEventRequest;
import org.osuswe.mdc.dto.GeneralResponse;
import org.osuswe.mdc.dto.GetUserEventResponse;
import org.osuswe.mdc.exception.InvalidArgumentException;
import org.osuswe.mdc.model.BriefUserBio;
import org.osuswe.mdc.model.User;
import org.osuswe.mdc.model.UserEvent;
import org.osuswe.mdc.services.UserEventService;
import org.osuswe.mdc.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resource/user_event")
@RequiredArgsConstructor
public class UserEventController {
    private final UserService userService;
    private final UserEventService userEventService;

    @PostMapping
    public ResponseEntity<GeneralResponse> updateUserEvent(@RequestHeader("Authorization") String bearerToken,
                                                           @RequestBody UpdateUserEventRequest request) {
        User user = userService.getUserFromBearerToken(bearerToken);
        request.setUserId(user.getId());

        userEventService.updateUserEventService(request);
        GeneralResponse resp = new GeneralResponse(HttpStatus.OK.value(), "");
        return ResponseEntity.ok(resp);
    }

    @RequestMapping(value = "/{event_id}", method = RequestMethod.GET)
    public ResponseEntity<GetUserEventResponse> getUserEvent(@RequestHeader("Authorization") String bearerToken,
                                                             @PathVariable("event_id") int eventId) {
        User user = userService.getUserFromBearerToken(bearerToken);
        UserEvent ue = userEventService.getUserEventService(user.getId(), eventId);

        if (ue == null) {
            throw new InvalidArgumentException("Cannot find event " + eventId + " associated with this user");
        }
        GetUserEventResponse resp = new GetUserEventResponse(HttpStatus.OK.value(), "");
        resp.setUserEvent(ue);
        return ResponseEntity.ok(resp);
    }

    @RequestMapping(value = "/attendees/{event_id}", method = RequestMethod.GET)
    public ResponseEntity<GetAttendeesResponse> getAttendees(@PathVariable("event_id") int eventId) {
        List<BriefUserBio> bios = userEventService.getAttendees(eventId);
        GetAttendeesResponse resp = new GetAttendeesResponse();
        resp.setAttendees(bios);
        return ResponseEntity.ok(resp);
    }
}
