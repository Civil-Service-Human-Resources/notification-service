package uk.gov.cshr.notificationservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "email_templates")
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplate {

    @Id
    private String emailTemplateName;

    @Column
    private String externalTemplateId;
}
