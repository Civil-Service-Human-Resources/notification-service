package uk.gov.cshr.notificationservice.dto.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    protected Map<String, String> personalisation;

    @Email(message="{message.recipient.valid}")
    @NotEmpty(message = "{message.recipient.required}")
    protected String recipient;

    protected String reference = UUID.randomUUID().toString();

}
