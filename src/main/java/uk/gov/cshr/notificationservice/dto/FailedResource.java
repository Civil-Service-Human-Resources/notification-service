package uk.gov.cshr.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FailedResource <T> {

    private T resource;
    private String reason;

}
