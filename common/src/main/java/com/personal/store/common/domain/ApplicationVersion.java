package com.personal.store.common.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "application_version")
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

    // getters/setters (omitted for brevity in this scaffold)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
    public String getVersionName() { return versionName; }
    public void setVersionName(String versionName) { this.versionName = versionName; }
    public Long getVersionCode() { return versionCode; }
    public void setVersionCode(Long versionCode) { this.versionCode = versionCode; }
}
