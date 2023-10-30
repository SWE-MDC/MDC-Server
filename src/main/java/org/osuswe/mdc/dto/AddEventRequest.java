package org.osuswe.mdc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddEventRequest {
    private String title;
    private String details;
    private String date; // yyyy-MM-dd hh:mm
    private String location;
    private Integer group_id;
}
