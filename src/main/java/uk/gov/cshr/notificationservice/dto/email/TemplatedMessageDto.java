package uk.gov.cshr.notificationservice.dto.email;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TemplatedMessageDto extends MessageDto {
    @NotEmpty(message = "{message.templateId.required}")
    private String templateId;
}
