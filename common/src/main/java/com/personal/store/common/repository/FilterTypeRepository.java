package com.personal.store.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal.store.common.domain.FilterType;

public interface FilterTypeRepository extends JpaRepository<FilterType, Long> {
}
