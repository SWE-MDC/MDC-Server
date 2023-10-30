package org.osuswe.mdc.services.impl;

import lombok.RequiredArgsConstructor;
import org.osuswe.mdc.dto.EventResponse;
import org.osuswe.mdc.model.AffinityGroup;
import org.osuswe.mdc.model.Event;
import org.osuswe.mdc.model.User;
import org.osuswe.mdc.model.VerificationCode;
import org.osuswe.mdc.repositories.AffinityGroupMapper;
import org.osuswe.mdc.repositories.EventMapper;
import org.osuswe.mdc.repositories.UserMapper;
import org.osuswe.mdc.services.EventService;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventMapper eventMapper;
    private final UserMapper userMapper;
    private final AffinityGroupMapper groupMapper;

    @Override
    public void addEvent(Event event) {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        event.setEvent_code(String.format("%06d", number));

        eventMapper.addEvent(event);
    }

    @Override
    public List<EventResponse> getEvents(int limit) {
        List<Event> events = eventMapper.getEvents(limit);
        List<EventResponse> responses = new ArrayList<>();

        for (Event e : events) {
            EventResponse resp = new EventResponse();
            DateFormat df = new SimpleDateFormat("MMMM dd, yyyy HH:mm");
            Optional<User> user = userMapper.getUserById(e.getOrganizer());
            Optional<AffinityGroup> group = groupMapper.getGroupById(e.getGroup_id());

            resp.setId(e.getId());
            resp.setTitle(e.getTitle());
            resp.setDetails(e.getDetails());
            resp.setDate(df.format(e.getDate()));
            resp.setLocation(e.getLocation());
            if (user.isPresent()) {
                User u = user.get();
                resp.setOrganizer(u.getFirst_name() + " " + u.getLast_name());
            }
            resp.setEventCode(e.getEvent_code());
            group.ifPresent(affinityGroup -> resp.setGroup(affinityGroup.getName()));

            responses.add(resp);
        }
        return responses;
    }
}
