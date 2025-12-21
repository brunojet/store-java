package com.personal.store.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "application_catalog")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
