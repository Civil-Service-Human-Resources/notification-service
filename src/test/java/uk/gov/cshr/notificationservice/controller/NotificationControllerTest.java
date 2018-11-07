package uk.gov.cshr.notificationservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.cshr.notificationservice.dto.EmailMessageDto;
import uk.gov.cshr.notificationservice.dto.factory.ValidationErrorsFactory;
import uk.gov.cshr.notificationservice.exception.NotificationServiceException;
import uk.gov.cshr.notificationservice.services.NotificationService;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest({NotificationController.class, ValidationErrorsFactory.class})
@WithMockUser(username = "user")
public class NotificationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    public void sendNotification() throws Exception {
        EmailMessageDto message = new EmailMessageDto();
        message.setRecipient("user@example.org");
        message.setPersonalisation(ImmutableMap.of("name", "test-name"));
        message.setTemplateId("template-id");
        message.setReference("message-reference");
        message.setReplyToId("reply-to-id");

        mockMvc.perform(
                post("/notifications/").with(csrf())
                        .content(objectMapper.writeValueAsString(message))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(notificationService).send(message);
    }

    @Test
    public void shouldReturnBadRequestIfRecipientOrTemplateIsMissing() throws Exception {
        EmailMessageDto message = new EmailMessageDto();
        message.setPersonalisation(ImmutableMap.of("name", "test-name"));
        message.setReference("message-reference");
        message.setReplyToId("reply-to-id");

        mockMvc.perform(
                post("/notifications/").with(csrf())
                        .content(objectMapper.writeValueAsString(message))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.size", equalTo(2)))
                .andExpect(jsonPath("$.errors[0].field", equalTo("recipient")))
                .andExpect(jsonPath("$.errors[0].details", equalTo("Recipient is required")))
                .andExpect(jsonPath("$.errors[1].field", equalTo("templateId")))
                .andExpect(jsonPath("$.errors[1].details", equalTo("templateId is required")));

        verifyZeroInteractions(notificationService);
    }

    @Test
    public void shouldReturnBadRequestIfRecipientIsNotValidEmailAddress() throws Exception {
        EmailMessageDto message = new EmailMessageDto();
        message.setPersonalisation(ImmutableMap.of("name", "test-name"));
        message.setTemplateId("template-id");
        message.setReference("message-reference");
        message.setReplyToId("reply-to-id");
        message.setRecipient("not-valid");

        mockMvc.perform(
                post("/notifications/").with(csrf())
                        .content(objectMapper.writeValueAsString(message))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.size", equalTo(1)))
                .andExpect(jsonPath("$.errors[0].field", equalTo("recipient")))
                .andExpect(jsonPath("$.errors[0].details", equalTo("Recipient is not a valid email address")));

        verifyZeroInteractions(notificationService);
    }

    @Test
    public void shouldReturnBadRequestIfSendingMessageFails() throws Exception {
        EmailMessageDto message = new EmailMessageDto();
        message.setRecipient("user@example.org");
        message.setPersonalisation(ImmutableMap.of("name", "test-name"));
        message.setTemplateId("template-id");
        message.setReference("message-reference");
        message.setReplyToId("reply-to-id");

        String errorMessage = "test error message";

        NotificationServiceException exception =
                new NotificationServiceException(errorMessage, mock(Throwable.class));

        doThrow(exception).when(notificationService).send(message);

        mockMvc.perform(
                post("/notifications/").with(csrf())
                        .content(objectMapper.writeValueAsString(message))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", equalTo(errorMessage)));
    }
}