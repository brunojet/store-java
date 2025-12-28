package com.personal.store.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal.store.common.domain.TerminalConfiguration;

public interface TerminalConfigurationRepository extends JpaRepository<TerminalConfiguration, Long> {
}
