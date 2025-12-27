package com.personal.store.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal.store.common.domain.abstraction.StorageObjectAbstraction;

public interface StorageObjectRepository extends JpaRepository<StorageObjectAbstraction, Long> {
}
