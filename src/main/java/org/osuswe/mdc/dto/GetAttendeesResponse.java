package org.osuswe.mdc.dto;

import lombok.Data;
import org.osuswe.mdc.exception.ErrorResponse;
import org.osuswe.mdc.model.EventAttendee;

import java.util.List;

@Data
public class GetAttendeesResponse extends ErrorResponse {
    public GetAttendeesResponse(int status, String message) {
        super(status, message);
    }

    private List<EventAttendee> attendees;
    private int notDecided;
    private int accept;
    private int reject;
    private int showUp;
}
