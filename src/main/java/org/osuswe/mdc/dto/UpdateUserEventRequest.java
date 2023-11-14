package org.osuswe.mdc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserEventRequest {
    private int eventId;
    private Integer userId;
    private int status;
    private int showUp;
    private String comments;
}
