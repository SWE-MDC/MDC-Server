package org.osuswe.mdc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.osuswe.mdc.exception.ErrorResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivateRequest {
    private String email;
}
