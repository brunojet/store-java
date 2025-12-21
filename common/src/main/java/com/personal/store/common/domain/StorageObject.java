package com.personal.store.common.domain;

import com.personal.store.common.domain.abstraction.BaseEntityAbstraction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "storage_object")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageObject extends BaseEntityAbstraction {
    @Column(name = "path", length = 255, nullable = false)
    private String path;

    @Column(name = "name", length = 40, nullable = false)
    private String name;

    @Column(name = "mime_type", length = 100, nullable = false)
    private String mimeType;

    @Column(name = "status")
    private Short status;

}
