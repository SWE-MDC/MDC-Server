package org.osuswe.mdc.model;

import lombok.Data;

@Data
public class VerificationCode {
    private long timestamp;
    private String code;
}
