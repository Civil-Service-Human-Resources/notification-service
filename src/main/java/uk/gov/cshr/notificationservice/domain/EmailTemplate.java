package uk.gov.cshr.notificationservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "email_templates")
public class EmailTemplate {

    @Id
    private String emailTemplateName;

    @Column
    private String externalTemplateId;
}
