package com.personal.store.common.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ApplicationProfileScreenshotId implements Serializable {
    @Column(name = "application_profile_id")
    private Long applicationProfileId;

    @Column(name = "application_image_id")
    private Long applicationImageId;
}
