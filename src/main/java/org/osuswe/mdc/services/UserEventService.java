package org.osuswe.mdc.services;

import org.osuswe.mdc.dto.UpdateUserEventRequest;
import org.osuswe.mdc.model.BriefUserBio;
import org.osuswe.mdc.model.UserEvent;

import java.util.List;

public interface UserEventService {
    void updateUserEventService(UpdateUserEventRequest userEvent);

    UserEvent getUserEventService(int userId, int eventId);

    List<BriefUserBio> getAttendees(int eventId);
}
