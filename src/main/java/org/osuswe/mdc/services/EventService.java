package org.osuswe.mdc.services;

import org.osuswe.mdc.dto.EventResponse;
import org.osuswe.mdc.model.Event;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EventService {
    void addEvent(Event event);

    List<EventResponse> getEvents(int limit);
}
