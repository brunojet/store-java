package com.personal.store.common.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "application_profile_history")
public class ApplicationProfileHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "application_contact_id", nullable = false)
    private Long applicationContactId;

    @Column(name = "application_detail_id", nullable = false)
    private Long applicationDetailId;

    @Column(name = "review_at")
    private LocalDateTime reviewAt;

    @Column(name = "production_at")
    private LocalDateTime productionAt;

    @Column(name = "deactivated_at")
    private LocalDateTime deactivatedAt;

    @Column(name = "deactivation_cause", columnDefinition = "longtext")
    private String deactivationCause;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
