package com.personal.store.common.domain;

import com.personal.store.common.domain.abstraction.StorageObjectAbstraction;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "application_image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationImage extends StorageObjectAbstraction {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Column(name = "application_id", nullable = false, insertable = false, updatable = false)
    private Long applicationId;

    @Column(name = "image_type")
    @Convert(converter = ApplicationImageTypeConverter.class)
    private ApplicationImageType imageType;
}