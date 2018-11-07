package uk.gov.cshr.notificationservice.controller;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.gov.cshr.notificationservice.dto.ValidationErrors;
import uk.gov.cshr.notificationservice.dto.factory.ValidationErrorsFactory;
import uk.gov.cshr.notificationservice.exception.NotificationServiceException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    private final ValidationErrorsFactory validationErrorsFactory;

    public ApiExceptionHandler(ValidationErrorsFactory validationErrorsFactory) {
        this.validationErrorsFactory = validationErrorsFactory;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ValidationErrors> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        LOGGER.error("Bad Request: ", e);

        return ResponseEntity.badRequest().body(validationErrorsFactory.create(e.getBindingResult().getFieldErrors()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotificationServiceException.class)
    protected ResponseEntity<Map<String, String>> handleNotificationServiceException(NotificationServiceException e) {

        LOGGER.error("Unable to send message", e);

        return ResponseEntity.badRequest().body(new HashMap<>(ImmutableMap.of("error", e.getMessage())));
    }
}
