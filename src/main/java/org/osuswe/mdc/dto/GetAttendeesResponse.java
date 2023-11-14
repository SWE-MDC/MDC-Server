package org.osuswe.mdc.dto;

import lombok.Data;
import org.osuswe.mdc.exception.ErrorResponse;
import org.osuswe.mdc.model.BriefUserBio;

import java.util.List;

@Data
public class GetAttendeesResponse extends ErrorResponse {
    private List<BriefUserBio> attendees;
}
