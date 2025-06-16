package uk.gov.cshr.notificationservice.dto.email;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NamedMessageDto extends MessageDto {

    @NotEmpty(message = "{message.name.required}")
    protected String name;

    public NamedMessageDto(Map<String, String> personalisation, String recipient, String reference, String name) {
        super(personalisation, recipient, reference);
        this.name = name;
    }
}
