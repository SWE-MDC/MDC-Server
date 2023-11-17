package org.osuswe.mdc.model;

import lombok.Data;

@Data
public class EventAttendee {
    private int id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private int status;
    private int showUp;
    private String comments;
}
