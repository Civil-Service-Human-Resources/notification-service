package uk.gov.cshr.notificationservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.gov.cshr.notificationservice.domain.EmailTemplate;
import uk.gov.cshr.notificationservice.dto.email.MessageDto;
import uk.gov.cshr.notificationservice.dto.email.TemplatedMessageDto;
import uk.gov.cshr.notificationservice.exception.EmailTemplateNotFound;
import uk.gov.cshr.notificationservice.exception.NotificationServiceException;
import uk.gov.cshr.notificationservice.repository.EmailTemplatesRepository;
import uk.gov.service.notify.NotificationClient;
import uk.gov.service.notify.NotificationClientException;
import uk.gov.service.notify.SendEmailResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final NotificationClient client;
    private final EmailTemplatesRepository emailTemplatesRepository;

    public void send(TemplatedMessageDto message) {
        try {
            SendEmailResponse response = client.sendEmail(
                    message.getTemplateId(),
                    message.getRecipient(),
                    message.getPersonalisation(),
                    message.getReference()
            );

            log.info("Notify email sent to: {}", response.getBody());
        } catch (NotificationClientException e) {
            throw new NotificationServiceException("Unable to send message", e);
        }
    }

    public void send(String emailName, MessageDto message) {
        EmailTemplate emailTemplate = emailTemplatesRepository.findById(emailName).orElseThrow(() -> new EmailTemplateNotFound(emailName));
        TemplatedMessageDto templatedMessageDto = (TemplatedMessageDto) message;
        templatedMessageDto.setTemplateId(emailTemplate.getExternalTemplateId());
        send(templatedMessageDto);
    }

}
