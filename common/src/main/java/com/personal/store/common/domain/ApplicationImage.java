package com.personal.store.common.domain;

import com.personal.store.common.domain.abstraction.StorageObjectAbstraction;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationImage extends StorageObjectAbstraction {
    @Column(name = "application_id", nullable = false)
    private Long applicationId;

    @Column(name = "image_type")
    @Convert(converter = ApplicationImageTypeConverter.class)
    private ApplicationImageType imageType;
}