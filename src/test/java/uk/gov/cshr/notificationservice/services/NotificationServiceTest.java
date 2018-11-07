package uk.gov.cshr.notificationservice.services;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.cshr.notificationservice.dto.MessageDto;
import uk.gov.cshr.notificationservice.exception.NotificationServiceException;
import uk.gov.service.notify.NotificationClient;
import uk.gov.service.notify.NotificationClientException;
import uk.gov.service.notify.SendEmailResponse;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceTest {
    @Mock
    private NotificationClient notificationClient;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    public void shouldSendMessage() throws NotificationClientException {
        String templateId = "template-id";
        String recipient = "message-recipient";
        Map<String, String> personalisation = ImmutableMap.of("name", "test-name");
        String reference = "message-reference";

        MessageDto message = new MessageDto();
        message.setTemplateId(templateId);
        message.setRecipient(recipient);
        message.setPersonalisation(personalisation);
        message.setReference(reference);

        SendEmailResponse response = mock(SendEmailResponse.class);
        when(notificationClient.sendEmail(templateId, recipient, personalisation, reference))
                .thenReturn(response);

        notificationService.send(message);

        verify(notificationClient).sendEmail(templateId, recipient, personalisation, reference);
    }

    @Test
    public void shouldThrowNotificationServiceException() throws NotificationClientException {
        String templateId = "template-id";
        String recipient = "message-recipient";
        Map<String, String> personalisation = ImmutableMap.of("name", "test-name");
        String reference = "message-reference";

        MessageDto message = new MessageDto();
        message.setTemplateId(templateId);
        message.setRecipient(recipient);
        message.setPersonalisation(personalisation);
        message.setReference(reference);

        NotificationClientException exception = mock(NotificationClientException.class);

        doThrow(exception)
                .when(notificationClient).sendEmail(templateId, recipient, personalisation, reference);

        try {
            notificationService.send(message);
            fail("Expected NotificationServiceException");
        } catch (NotificationServiceException e) {
            assertEquals("Unable to send message", e.getMessage());
            assertTrue(e.getCause() instanceof NotificationClientException);
        }
    }
}