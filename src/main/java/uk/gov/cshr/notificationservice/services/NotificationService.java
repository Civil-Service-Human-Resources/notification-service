package uk.gov.cshr.notificationservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.gov.service.notify.NotificationClient;
import uk.gov.service.notify.NotificationClientException;
import uk.gov.service.notify.SendEmailResponse;

import java.util.HashMap;

@Service
public class NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    @Value("${govNotify.key}")
    private String apiKey;

    @Autowired
    private NotificationClient client;

    public NotificationService(NotificationClient client) {
        this.client = client;
    }

    public void sendEmailNotification(String templateId, String emailAddress, HashMap<String, String> personalisation, String reference, String emailReplyToId) throws NotificationClientException {
        SendEmailResponse response = client.sendEmail(
                templateId,
                emailAddress,
                personalisation,
                reference,
                emailReplyToId
        );

        LOGGER.info("Notify email sent to: {}", response.getBody());
    }
}
