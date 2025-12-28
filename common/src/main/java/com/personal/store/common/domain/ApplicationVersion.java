package com.personal.store.common.domain;

import java.time.LocalDateTime;

import com.personal.store.common.domain.abstraction.SimpleEntityAbstraction;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "application_version")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationVersion extends SimpleEntityAbstraction {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
        @JoinColumn(
            name = "application_id",
            referencedColumnName = "application_id",
            updatable = false
        ),
        @JoinColumn(
            name = "terminal_configuration_id",
            referencedColumnName = "terminal_configuration_id",
            updatable = false
        )
    })
    private ApplicationConfiguration applicationConfiguration;

    @Column(name = "version_name", length = 255, nullable = false)
    private String versionName;

    @Column(name = "version_code", nullable = false)
    private Long versionCode;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "pilot_at")
    private LocalDateTime pilotAt;

    @Column(name = "production_at")
    private LocalDateTime productionAt;

    @Column(name = "stage", columnDefinition = "smallint default 0")
    @Convert(converter = ApplicationVersionStageConverter.class)
    private ApplicationVersionStage stage;
}
