package org.osuswe.mdc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventDetailsRequest {
    private int eventId;
    private Integer userId;
    private int status;
    private String comments;
}
