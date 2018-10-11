package uk.gov.cshr.notificationservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/notifications/")
public class NotificationController {

    @PostMapping
    public ResponseEntity sendNotifications(){
        return new ResponseEntity<>(OK);
    }
}
