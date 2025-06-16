package uk.gov.cshr.notificationservice.dto.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.gov.cshr.notificationservice.dto.FailedResource;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BulkSendEmailResponse {

    private List<String> successfulEmailRefs;
    private List<FailedResource<NamedMessageDto>> failedEmails;

}
