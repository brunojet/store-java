package com.personal.store.common.domain;

import com.personal.store.common.domain.abstraction.BaseEntityAbstraction;

import jakarta.persistence.Column;
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
@Table(name = "image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image extends BaseEntityAbstraction {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_object_id", nullable = false)
    private StorageObject storageObject;

    @Column(name = "image_type")
    private Short imageType;
}
