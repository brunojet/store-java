package com.personal.store.common.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "application_catalog")
public class ApplicationCatalog {
    @Id
    @Column(name = "integration_type_id")
    private Long integrationTypeId;

    @Id
    @Column(name = "terminal_model_id")
    private Long terminalModelId;

    @Id
    @Column(name = "stage")
    private Short stage;

    @Id
    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "application_profile_id")
    private Long applicationProfileId;

    @Column(name = "application_version_id")
    private Long applicationVersionId;

    @Column(name = "active")
    private Boolean active;

    // Note: composite PK mapping omitted for brevity; use @IdClass if needed
    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
}
