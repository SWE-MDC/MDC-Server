package org.osuswe.mdc.dto;

import lombok.Data;
import org.osuswe.mdc.exception.ErrorResponse;
import org.osuswe.mdc.model.Role;
@Data
public class GetRoleResponse extends ErrorResponse {
    private Role data;
    public GetRoleResponse(int status, String message) {
        super(status, message);
    }
}
