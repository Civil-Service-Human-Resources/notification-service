package uk.gov.cshr.notificationservice.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Map;
import java.util.UUID;

@Data
public class MessageDto {
    private Map<String, String> personalisation;

    @Email(message="{message.recipient.valid}")
    @NotEmpty(message = "{message.recipient.required}")
    private String recipient;

    @NotEmpty(message = "{message.templateId.required}")
    private String templateId;

    private String reference = UUID.randomUUID().toString();
}
