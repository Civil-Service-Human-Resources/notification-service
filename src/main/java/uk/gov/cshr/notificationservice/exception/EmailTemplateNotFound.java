package uk.gov.cshr.notificationservice.exception;

public class EmailTemplateNotFound extends RuntimeException {
    public EmailTemplateNotFound(String emailId) {
        super(String.format("Email template with ID '%s' not found", emailId));
    }
}
