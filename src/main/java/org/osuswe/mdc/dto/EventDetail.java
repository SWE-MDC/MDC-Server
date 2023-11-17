package org.osuswe.mdc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventDetail {
    private int userId;
    private int eventId;
    private String title;
    private String details;
    private String date;
    private int status;
    private int showUp;
    private String comments;
    private String location;
    private String organizer;
    private String eventCode;
}
