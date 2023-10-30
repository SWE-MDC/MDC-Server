package org.osuswe.mdc.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class EventResponse {
    private int id;
    private String title;
    private String details;
    private String date;
    String location;
    String organizer;
    String eventCode;
    String group;
}
