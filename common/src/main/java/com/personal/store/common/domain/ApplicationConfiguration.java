package com.personal.store.common.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "application_configuration")
@IdClass(ApplicationConfigurationId.class)
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

    // constructors/getters/setters
    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
    public Long getIntegrationTypeId() { return integrationTypeId; }
    public void setIntegrationTypeId(Long integrationTypeId) { this.integrationTypeId = integrationTypeId; }
    public Long getTerminalModelId() { return terminalModelId; }
    public void setTerminalModelId(Long terminalModelId) { this.terminalModelId = terminalModelId; }
}
