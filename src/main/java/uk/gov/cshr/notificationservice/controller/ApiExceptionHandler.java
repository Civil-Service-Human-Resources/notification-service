package uk.gov.cshr.notificationservice.controller;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.gov.cshr.notificationservice.dto.ValidationErrors;
import uk.gov.cshr.notificationservice.dto.factory.ValidationErrorsFactory;
import uk.gov.cshr.notificationservice.exception.EmailTemplateNotFound;
import uk.gov.cshr.notificationservice.exception.NotificationServiceException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ApiExceptionHandler {
    private final ValidationErrorsFactory validationErrorsFactory;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ValidationErrors> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Bad Request: ", e);

        return ResponseEntity.badRequest().body(validationErrorsFactory.create(e.getBindingResult().getFieldErrors()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotificationServiceException.class)
    protected ResponseEntity<Map<String, String>> handleNotificationServiceException(NotificationServiceException e) {

        log.error("Unable to send message", e);

        return ResponseEntity.badRequest().body(new HashMap<>(ImmutableMap.of("error", e.getMessage())));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailTemplateNotFound.class)
    protected ResponseEntity<Map<String, String>> handleEmailTemplateNotFoundException(EmailTemplateNotFound e) {

        log.error("Template not found", e);

        return ResponseEntity.badRequest().body(new HashMap<>(ImmutableMap.of("error", e.getMessage())));
    }
}
