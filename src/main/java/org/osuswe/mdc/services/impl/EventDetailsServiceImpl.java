package org.osuswe.mdc.services.impl;

import lombok.RequiredArgsConstructor;
import org.osuswe.mdc.dto.UpdateEventDetailsRequest;
import org.osuswe.mdc.exception.InvalidArgumentException;
import org.osuswe.mdc.model.EventAttendee;
import org.osuswe.mdc.model.Event;
import org.osuswe.mdc.model.User;
import org.osuswe.mdc.model.UserEvent;
import org.osuswe.mdc.repositories.EventMapper;
import org.osuswe.mdc.repositories.UserEventMapper;
import org.osuswe.mdc.repositories.UserMapper;
import org.osuswe.mdc.services.EventDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventDetailsServiceImpl implements EventDetailsService {
    private final EventMapper eventMapper;
    private final UserEventMapper userEventMapper;
    private final UserMapper userMapper;

    @Override
    public void checkIn(int userId, int eventId, String checkinCode) {
        Event event = eventMapper.getEvent(eventId);
        if (event.getEvent_code().equals(checkinCode)) {
            var ue = userEventMapper.getUserEvent(userId, eventId);

            if (ue.isPresent()) {
                ue.get().setShow_up(1);
                userEventMapper.updateUserEvent(ue.get());
            } else {
                throw new InvalidArgumentException("You haven't submitted your plan, so you cannot checkin");
            }
        } else {
            throw new InvalidArgumentException("Invalid checkin code");
        }
    }

    @Override
    public void updateEventDetails(UpdateEventDetailsRequest req) {

        if (req.getStatus() < 0 || req.getStatus() > 2) {
            throw new InvalidArgumentException("Invalid status code: " + req.getStatus());
        }

        var ue = userEventMapper.getUserEvent(req.getUserId(), req.getEventId());
        boolean new_insertion = ue.isEmpty();
        if (ue.isEmpty()) {
            ue = Optional.of(new UserEvent());
        }
        var inst = ue.get();
        inst.setUser_id(req.getUserId());
        inst.setEvent_id(req.getEventId());
        inst.setStatus(req.getStatus());
        inst.setComments(req.getComments());
        if (new_insertion) {
            userEventMapper.addUserEvent(inst);
        } else {
            userEventMapper.updateUserEvent(inst);
        }
    }

    @Override
    public Optional<UserEvent> getEventDetails(int userId, int eventId) {
        return userEventMapper.getUserEvent(userId, eventId);
    }

    @Override
    public List<EventAttendee> getAttendees(int eventId) {
        List<UserEvent> userEvents = userEventMapper.getUserEvents(eventId);
        List<EventAttendee> attendees = new ArrayList<>();

        for (var ue: userEvents) {
            var user = userMapper.getUserById(ue.getUser_id());
            if (user.isPresent()) {
                User u = user.get();
                EventAttendee attendee = new EventAttendee();
                attendee.setId(ue.getId());
                attendee.setUserName(u.getUsername());
                attendee.setEmail(u.getEmail());
                attendee.setFirstName(u.getFirst_name());
                attendee.setLastName(u.getLast_name());
                attendee.setStatus(ue.getStatus());
                attendee.setShowUp(ue.getShow_up());
                attendee.setComments(ue.getComments());
                attendees.add(attendee);
            }
        }
        return attendees;
    }
}
