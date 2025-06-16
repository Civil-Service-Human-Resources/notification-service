package uk.gov.cshr.notificationservice.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.cshr.notificationservice.domain.EmailTemplate;
import uk.gov.cshr.notificationservice.dto.email.BulkSendEmailResponse;
import uk.gov.cshr.notificationservice.dto.email.NamedMessageDto;
import uk.gov.cshr.notificationservice.repository.EmailTemplatesRepository;
import uk.gov.service.notify.NotificationClient;
import uk.gov.service.notify.NotificationClientException;
import uk.gov.service.notify.SendEmailResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    @Mock
    private NotificationClient notificationClient;

    @Mock
    private EmailTemplatesRepository emailTemplatesRepository;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void testSendBulk() throws NotificationClientException {
        when(emailTemplatesRepository.findAllById(List.of("TEMPLATE_1", "TEMPLATE_2", "TEMPLATE_3")))
                .thenReturn(List.of(new EmailTemplate("TEMPLATE_1", "UID_1"),
                                    new EmailTemplate("TEMPLATE_3", "UID_3")));

        SendEmailResponse successResponse = mock(SendEmailResponse.class);
        UUID uuid = UUID.randomUUID();
        when(successResponse.getNotificationId()).thenReturn(uuid);
        when(successResponse.getBody()).thenReturn("Sent");
        when(notificationClient.sendEmail("UID_1", "uid1@email.com", Map.of(), "ref1"))
                .thenReturn(successResponse);
        when(notificationClient.sendEmail("UID_3", "uid3@email.com", Map.of(), "ref3"))
                .thenThrow(new NotificationClientException("Notification client exception"));

        BulkSendEmailResponse response = emailService.send(List.of(
                new NamedMessageDto(Map.of(), "uid1@email.com", "ref1", "TEMPLATE_1"),
                new NamedMessageDto(Map.of(), "uid2@email.com", "ref2", "TEMPLATE_2"),
                new NamedMessageDto(Map.of(), "uid3@email.com", "ref3", "TEMPLATE_3")
        ));
        assertEquals(2, response.getFailedEmails().size());
        assertEquals("Email template with ID 'TEMPLATE_2' not found", response.getFailedEmails().get(0).getReason());
        assertEquals("Unable to send message", response.getFailedEmails().get(1).getReason());
        assertEquals(1, response.getSuccessfulEmailRefs().size());
        assertEquals(uuid.toString(), response.getSuccessfulEmailRefs().get(0));
    }

}
