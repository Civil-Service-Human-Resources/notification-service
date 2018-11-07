package uk.gov.cshr.notificationservice.exception;

public class NotificationServiceException extends RuntimeException {
    public NotificationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
