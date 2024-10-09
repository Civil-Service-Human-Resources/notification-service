package uk.gov.cshr.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.cshr.notificationservice.domain.EmailTemplate;

public interface EmailTemplatesRepository extends JpaRepository<EmailTemplate, String> {
}
