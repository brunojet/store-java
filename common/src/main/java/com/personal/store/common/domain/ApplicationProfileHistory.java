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
@Table(name = "application_profile_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
