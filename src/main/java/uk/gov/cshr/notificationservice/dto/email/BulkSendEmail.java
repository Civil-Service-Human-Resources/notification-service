package uk.gov.cshr.notificationservice.dto.email;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkSendEmail {

    @Valid
    @Size(min = 1, max = 20)
    private List<NamedMessageDto> emails;

}
