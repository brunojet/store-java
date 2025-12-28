package com.personal.store.common.repository;

import com.personal.store.common.domain.ApplicationConfiguration;
import com.personal.store.common.domain.ApplicationConfigurationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationConfigurationRepository extends JpaRepository<ApplicationConfiguration, ApplicationConfigurationId> {
}
