package org.osuswe.mdc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private int id;
    private String title;
    private String details;
    private Date date;
    String location;
    Integer organizer;
    String event_code;
    Integer group_id;
}
