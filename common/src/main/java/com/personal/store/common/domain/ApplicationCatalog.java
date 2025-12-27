package com.personal.store.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "application_catalog")
@IdClass(ApplicationCatalogId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCatalog {
    @Id
    @Column(name = "application_id")
    private Long applicationId;

    @Id
    @Column(name = "terminal_configuration_id")
    private Long terminalConfigurationId;

    @Id
    @Column(name = "stage")
    @Enumerated(EnumType.ORDINAL)
    private ApplicationStage stage;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
        @JoinColumn(
            name = "application_id",
            referencedColumnName = "application_id",
            insertable = false,
            updatable = false
        ),
        @JoinColumn(
            name = "terminal_configuration_id",
            referencedColumnName = "terminal_configuration_id",
            insertable = false,
            updatable = false
        )
    })
    private ApplicationConfiguration applicationConfiguration;

    @Column(name = "application_profile_id")
    private Long applicationProfileId;

    @Column(name = "application_version_id")
    private Long applicationVersionId;

    @Column(name = "active")
    private Boolean active;
}
