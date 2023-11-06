package org.osuswe.mdc.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.osuswe.mdc.exception.ErrorResponse;
import org.osuswe.mdc.model.Role;

import java.util.List;
@Data
public class JwtAuthenticationResponse extends ErrorResponse {
    private String token;
    private Role role;

    public JwtAuthenticationResponse(int status, String message) {
        super(status, message);
    }
}
