package org.osuswe.mdc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.osuswe.mdc.exception.ErrorResponse;
public class ActivateResponse extends ErrorResponse {
    public ActivateResponse(int status, String message) {
        super(status, message);
    }
}
