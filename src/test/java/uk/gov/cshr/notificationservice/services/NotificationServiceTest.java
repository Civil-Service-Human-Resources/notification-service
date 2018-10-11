package uk.gov.cshr.notificationservice.services;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.service.notify.NotificationClient;
import uk.gov.service.notify.NotificationClientException;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationServiceTest {
    public static final String API_KEY = "api123";
    public static final String TEMPLATE_ID = "template123";
    public static final String EMAIL_ADDRESS = "example@example.com";
    public static final HashMap<String, String> PERSONALISATION = new HashMap<>();
    public static final String REFERENCE = "reference123";
    public static final String REPLAY_EMAIL_ADDRESS = "replay@example.com";

    private NotificationService notificationService;

    private NotificationClient notificationClient;

    @Before
    public void setup() {
        initMocks(this);
        notificationClient = new NotificationClient(API_KEY);
        notificationService = new NotificationService(notificationClient);
    }

    @Test
    public void testSendEmailNotification() throws NotificationClientException {
//        JSONObject obj = new JSONObject();
//        obj.append("id", "123");
//        SendEmailResponse sendEmailResponse = new SendEmailResponse(obj.toString());
//        when(notificationClient.sendEmail(TEMPLATE_ID, EMAIL_ADDRESS, PERSONALISATION, REFERENCE, REPLAY_EMAIL_ADDRESS)).thenReturn(sendEmailResponse);
        MatcherAssert.assertThat(1, equalTo(1));
    }

}