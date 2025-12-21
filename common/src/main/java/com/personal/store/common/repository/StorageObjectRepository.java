package com.personal.store.common.repository;

import com.personal.store.common.domain.StorageObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageObjectRepository extends JpaRepository<StorageObject, Long> {
}
