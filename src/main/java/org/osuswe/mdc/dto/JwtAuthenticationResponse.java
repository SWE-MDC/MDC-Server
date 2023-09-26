package org.osuswe.mdc.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.osuswe.mdc.exception.ErrorResponse;

import java.util.List;
@Data
public class JwtAuthenticationResponse extends ErrorResponse {
    private String token;

    public JwtAuthenticationResponse(int status, String message) {
        super(status, message);
    }
}
