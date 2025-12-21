package com.personal.store.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "application_version")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "longtext")
    private String description;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "application_id", nullable = false)
    private Long applicationId;

    @Column(name = "integration_type_id", nullable = false)
    private Long integrationTypeId;

    @Column(name = "terminal_model_id", nullable = false)
    private Long terminalModelId;

    @Column(name = "version_name", nullable = false)
    private String versionName;

    @Column(name = "version_code", nullable = false)
    private Long versionCode;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "pilot_at")
    private LocalDateTime pilotAt;

    @Column(name = "production_at")
    private LocalDateTime productionAt;

    @Column(name = "deactivated_at")
    private LocalDateTime deactivatedAt;

    @Column(name = "deactivation_cause", length = 255)
    private String deactivationCause;
}
