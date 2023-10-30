package org.osuswe.mdc.dto;

import org.osuswe.mdc.exception.ErrorResponse;

public class GeneralResponse extends ErrorResponse {
    public GeneralResponse(int status, String message) {
        super(status, message);
    }
}
