package com.personal.store.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "application_catalog")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCatalog {
    @EmbeddedId
    private ApplicationCatalogId id = new ApplicationCatalogId();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("applicationConfigurationId")
    @JoinColumns({
        @JoinColumn(
            name = "application_id",
            referencedColumnName = "application_id"
        ),
        @JoinColumn(
            name = "terminal_configuration_id",
            referencedColumnName = "terminal_configuration_id"
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
