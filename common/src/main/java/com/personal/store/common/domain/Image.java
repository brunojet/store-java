package com.personal.store.common.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_object_id", nullable = false)
    private StorageObject storageObject;

    @Column(name = "image_type")
    private Short imageType;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public StorageObject getStorageObject() { return storageObject; }
    public void setStorageObject(StorageObject storageObject) { this.storageObject = storageObject; }
    public Short getImageType() { return imageType; }
    public void setImageType(Short imageType) { this.imageType = imageType; }
}
