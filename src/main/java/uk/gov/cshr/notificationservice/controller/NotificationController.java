package uk.gov.cshr.notificationservice.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.cshr.notificationservice.dto.EmailMessageDto;
import uk.gov.cshr.notificationservice.services.NotificationService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/notifications/")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity sendNotification(@Valid @RequestBody EmailMessageDto message){
        notificationService.send(message);

        return new ResponseEntity<>(OK);
    }
}
