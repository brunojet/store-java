package com.personal.store.common.domain.abstraction;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class StorageObjectAbstraction extends BaseEntityAbstraction {
    @Column(name = "file_name", length = 80, nullable = false)
    private String name;

    @Column(name = "mime_type", length = 255, nullable = false)
    private String mimeType;

    @Column(name = "size_in_bytes", nullable = false)
    private Long sizeInBytes;

    @Column(name = "file_hash", length = 32, nullable = false)
    private Byte[] fileHash;

    @Column(name = "status")
    @Convert(converter = StorageObjectStatusConverter.class)
    private StorageObjectStatus status;

}
