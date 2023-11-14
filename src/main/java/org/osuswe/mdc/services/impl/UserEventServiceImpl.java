package org.osuswe.mdc.services.impl;

import lombok.RequiredArgsConstructor;
import org.osuswe.mdc.dto.UpdateUserEventRequest;
import org.osuswe.mdc.exception.InvalidArgumentException;
import org.osuswe.mdc.model.BriefUserBio;
import org.osuswe.mdc.model.User;
import org.osuswe.mdc.model.UserEvent;
import org.osuswe.mdc.repositories.UserEventMapper;
import org.osuswe.mdc.repositories.UserMapper;
import org.osuswe.mdc.services.UserEventService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserEventServiceImpl implements UserEventService {
    private final UserEventMapper userEventMapper;
    private final UserMapper userMapper;

    @Override
    public void updateUserEventService(UpdateUserEventRequest req) {
        UserEvent ue;
        if (req.getStatus() < 0 || req.getStatus() > 2) {
            throw new InvalidArgumentException("Invalid status code: " + req.getStatus());
        }
        if (req.getShowUp() != 0 && req.getShowUp() != 1) {
            throw new InvalidArgumentException("Invalid show up value: " + req.getShowUp());
        }

        ue = userEventMapper.getUserEvent(req.getUserId(), req.getEventId());
        boolean new_insertion = ue == null;
        if (ue == null) {
            ue = new UserEvent();
            ue.setUser_id(req.getUserId());
            ue.setEvent_id(req.getEventId());
        }
        ue.setStatus(req.getStatus());
        ue.setShow_up(req.getShowUp());
        ue.setComments(req.getComments());
        if (new_insertion) {
            userEventMapper.addUserEvent(ue);
        } else {
            userEventMapper.updateUserEvent(ue);
        }
    }

    @Override
    public UserEvent getUserEventService(int userId, int eventId) {
        return userEventMapper.getUserEvent(userId, eventId);
    }

    @Override
    public List<BriefUserBio> getAttendees(int eventId) {
        List<Integer> userIds = userEventMapper.getAttendees(eventId);
        List<BriefUserBio> bios = new ArrayList<>();

        for (Integer id : userIds) {
            var user = userMapper.getUserById(id);
            if (user.isPresent()) {
                User u = user.get();
                BriefUserBio bio = new BriefUserBio();
                bio.setUserName(u.getUsername());
                bio.setEmail(u.getEmail());
                bio.setFirstName(u.getFirst_name());
                bio.setLastName(u.getLast_name());
                bios.add(bio);
            }
        }
        return bios;
    }
}
