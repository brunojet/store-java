package com.personal.store.common.domain;

import java.time.LocalDateTime;

import com.personal.store.common.domain.abstraction.BaseEntityAbstraction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "application_profile_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationProfileHistory extends BaseEntityAbstraction {
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
