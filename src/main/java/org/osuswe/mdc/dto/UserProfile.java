package org.osuswe.mdc.dto;

import lombok.Data;

@Data
public class UserProfile {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String pronouns;
    private String year;
    private String major;
}
