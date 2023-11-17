package org.osuswe.mdc.services;

import org.osuswe.mdc.dto.UpdateEventDetailsRequest;
import org.osuswe.mdc.model.EventAttendee;
import org.osuswe.mdc.model.UserEvent;

import java.util.List;
import java.util.Optional;

public interface EventDetailsService {

    void checkIn(int userId, int eventId, String checkinCode);
    void updateEventDetails(UpdateEventDetailsRequest userEvent);

    Optional<UserEvent> getEventDetails(int userId, int eventId);

    List<EventAttendee> getAttendees(int eventId);
}
