package com.personal.store.common.domain;

import com.personal.store.common.domain.abstraction.AuditAbstraction;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.MapsId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "application_catalog", indexes = {
        @Index(name = "ix_app_catalog_app_term_stage", columnList = "application_id, terminal_configuration_id, stage"),
        @Index(name = "ix_app_catalog_stage_term_app", columnList = "stage, terminal_configuration_id, application_id"),
        @Index(name = "ix_app_catalog_deleted_created", columnList = "deleted_at, created_at"),
        @Index(name = "ix_app_catalog_deleted_updated", columnList = "deleted_at, updated_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCatalog extends AuditAbstraction {
    @EmbeddedId
    private ApplicationCatalogId id = new ApplicationCatalogId();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("applicationConfigurationId")
    @JoinColumns({
            @JoinColumn(name = "application_id", referencedColumnName = "application_id"),
            @JoinColumn(name = "terminal_configuration_id", referencedColumnName = "terminal_configuration_id")
    })
    private ApplicationConfiguration applicationConfiguration;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "application_profile_id", referencedColumnName = "id", nullable = false)
    private ApplicationProfile applicationProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_version_id", referencedColumnName = "id")
    private ApplicationVersion applicationVersion;

    @Column(name = "active")
    private Boolean active;
}
