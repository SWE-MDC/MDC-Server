package org.osuswe.mdc.controllers;

import lombok.RequiredArgsConstructor;
import org.osuswe.mdc.dto.*;
import org.osuswe.mdc.model.Event;
import org.osuswe.mdc.model.User;
import org.osuswe.mdc.services.EventService;
import org.osuswe.mdc.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/api/v1/resource/event")
@RequiredArgsConstructor
public class EventController {
    private final UserService userService;
    private final EventService eventService;

    @PostMapping("/add")
    public ResponseEntity<GeneralResponse> addEvent(@RequestHeader("Authorization") String bearerToken,
                                                    @RequestBody AddEventRequest request) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        User user = userService.getUserFromBearerToken(bearerToken);
        Event event = Event.builder().title(request.getTitle())
                .details(request.getDetails())
                .date(sdf.parse(request.getDate()))
                .location(request.getLocation())
                .group_id(request.getGroup_id())
                .organizer(user.getId())
                .build();

        eventService.addEvent(event);

        GeneralResponse resp = new GeneralResponse(HttpStatus.OK.value(), "Add event successfully");

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/get")
    public ResponseEntity<GetEventsResponse> getEvents(@RequestParam("limit") Integer limit) {
        List<EventResponse> events = eventService.getEvents(limit);
        GetEventsResponse resp = new GetEventsResponse(HttpStatus.OK.value(), "", events);
        return ResponseEntity.ok(resp);
    }


}
