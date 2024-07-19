package uk.gov.cshr.notificationservice.dto.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class MessageDto {
    protected Map<String, String> personalisation;

    @Email(message="{message.recipient.valid}")
    @NotEmpty(message = "{message.recipient.required}")
    protected String recipient;

    protected String reference = UUID.randomUUID().toString();
}
