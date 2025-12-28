package com.personal.store.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "application_profile_screenshot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationProfileScreenshot {
    @EmbeddedId
    private ApplicationProfileScreenshotId id = new ApplicationProfileScreenshotId();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("applicationProfileId")
    @JoinColumn(name = "application_profile_id", referencedColumnName = "id", nullable = false)
    private ApplicationProfile applicationProfile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("applicationImageId")
    @JoinColumn(name = "application_image_id", referencedColumnName = "id", nullable = false)
    private ApplicationImage applicationImage;

    @Column(name = "position", nullable = false)
    private Integer position;
}
