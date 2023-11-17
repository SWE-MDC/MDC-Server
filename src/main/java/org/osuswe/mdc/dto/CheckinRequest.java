package org.osuswe.mdc.dto;

import lombok.Data;

@Data
public class CheckinRequest {
    private int eventId;
    private String checkinCode;
}
