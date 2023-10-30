package org.osuswe.mdc.dto;

import lombok.Data;
import org.osuswe.mdc.exception.ErrorResponse;
import org.osuswe.mdc.model.Event;

import java.util.List;

@Data
public class GetEventsResponse extends ErrorResponse {
    private List<EventResponse> events;

    public GetEventsResponse(int status, String message,
                             List<EventResponse> events) {
        super(status, message);
        this.events = events;
    }
}
