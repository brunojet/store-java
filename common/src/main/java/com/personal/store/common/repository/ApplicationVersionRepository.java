package com.personal.store.common.repository;

import com.personal.store.common.domain.ApplicationVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationVersionRepository extends JpaRepository<ApplicationVersion, Long> {
}
