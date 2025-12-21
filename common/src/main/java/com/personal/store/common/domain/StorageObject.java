package com.personal.store.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "storage_object")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "path", length = 255, nullable = false)
    private String path;

    @Column(name = "name", length = 40, nullable = false)
    private String name;

    @Column(name = "mime_type", length = 100, nullable = false)
    private String mimeType;

    @Column(name = "status")
    private Short status;

}
