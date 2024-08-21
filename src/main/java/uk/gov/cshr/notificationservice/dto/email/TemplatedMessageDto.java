package uk.gov.cshr.notificationservice.dto.email;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class TemplatedMessageDto extends MessageDto {
    @NotEmpty(message = "{message.templateId.required}")
    private String templateId;

    public TemplatedMessageDto(Map<String, String> personalisation, String recipient,
                               String reference, String templateId) {
        super(personalisation, recipient, reference);
        this.templateId = templateId;
    }
}
