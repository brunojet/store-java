package com.personal.store.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "application_profile_screenshot")
@IdClass(ApplicationProfileScreenshotId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationProfileScreenshot {
    @Id
    @Column(name = "application_profile_id")
    private Long applicationProfileId;

    @Id
    @Column(name = "application_image_id")
    private Long applicationImageId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "application_profile_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ApplicationProfile applicationProfile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "application_image_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ApplicationImage applicationImage;

    @Column(name = "position", nullable = false)
    private Integer position;
}
