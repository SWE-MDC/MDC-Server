package org.osuswe.mdc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.osuswe.mdc.exception.ErrorResponse;
import org.osuswe.mdc.model.UserEvent;

@Data
public class GetUserEventResponse extends ErrorResponse {
    private UserEvent userEvent;

    public GetUserEventResponse(int status, String message) {
        super(status, message);
    }
}
