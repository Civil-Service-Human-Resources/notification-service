package uk.gov.cshr.notificationservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.gov.cshr.notificationservice.dto.email.MessageDto;
import uk.gov.cshr.notificationservice.services.EmailService;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/notifications/emails")
@RequiredArgsConstructor
public class EmailNotificationController {

    private final EmailService notificationService;

    @PostMapping(path = "/{name}/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    @ResponseBody
    public void sendEmail(@PathVariable String name, @Valid @RequestBody MessageDto message){
        notificationService.send(name, message);
    }

}
