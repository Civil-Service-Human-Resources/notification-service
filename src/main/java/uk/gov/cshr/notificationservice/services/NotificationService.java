package uk.gov.cshr.notificationservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.gov.cshr.notificationservice.dto.EmailMessageDto;
import uk.gov.cshr.notificationservice.exception.NotificationServiceException;
import uk.gov.service.notify.NotificationClient;
import uk.gov.service.notify.NotificationClientException;
import uk.gov.service.notify.SendEmailResponse;

@Service
public class NotificationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationClient client;

    public NotificationService(NotificationClient client) {
        this.client = client;
    }

    public void send(EmailMessageDto message) {
        try {
            SendEmailResponse response = client.sendEmail(
                    message.getTemplateId(),
                    message.getRecipient(),
                    message.getPersonalisation(),
                    message.getReference(),
                    message.getReplyToId()
            );

            LOGGER.info("Notify email sent to: {}", response.getBody());
        } catch (NotificationClientException e) {
            throw new NotificationServiceException("Unable to send message", e);
        }
    }
}
