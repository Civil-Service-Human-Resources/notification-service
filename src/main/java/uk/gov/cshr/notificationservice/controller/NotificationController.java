package uk.gov.cshr.notificationservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.gov.cshr.notificationservice.dto.email.TemplatedMessageDto;
import uk.gov.cshr.notificationservice.services.EmailService;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/notifications/")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailService notificationService;

    @PostMapping(path = "/email", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    @ResponseBody
    public void sendNotification(@Valid @RequestBody TemplatedMessageDto message){
        notificationService.send(message);
    }
}
