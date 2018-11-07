package uk.gov.cshr.notificationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.service.notify.NotificationClient;

@Configuration
public class ApplicationConfig {
    @Value("${govNotify.key}")
    private String apiKey;

    @Bean
    public NotificationClient client() {
        return new NotificationClient(apiKey);
    }
}
