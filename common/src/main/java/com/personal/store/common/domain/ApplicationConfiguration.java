package com.personal.store.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "application_configuration")
@IdClass(ApplicationConfigurationId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationConfiguration {
    @Id
    @Column(name = "application_id")
    private Long applicationId;

    @Id
    @Column(name = "integration_type_id")
    private Long integrationTypeId;

    @Id
    @Column(name = "terminal_model_id")
    private Long terminalModelId;
}
