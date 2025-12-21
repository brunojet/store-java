package com.personal.store.common.repository;

import com.personal.store.common.domain.IntegrationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntegrationTypeRepository extends JpaRepository<IntegrationType, Long> {
}
