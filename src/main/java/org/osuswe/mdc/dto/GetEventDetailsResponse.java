package org.osuswe.mdc.dto;

import lombok.Data;
import org.osuswe.mdc.exception.ErrorResponse;

@Data
public class GetEventDetailsResponse extends ErrorResponse {
//    private UserEvent userEvent;
    EventDetail eventDetails;
    public GetEventDetailsResponse(int status, String message) {
        super(status, message);
    }
}
