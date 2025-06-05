package uk.gov.cshr.notificationservice.dto.email;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NamedMessageDto extends MessageDto {

    @NotEmpty(message = "{message.name.required}")
    protected String name;

}
