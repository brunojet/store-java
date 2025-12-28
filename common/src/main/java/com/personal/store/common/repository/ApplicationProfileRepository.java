package com.personal.store.common.repository;

import com.personal.store.common.domain.ApplicationProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationProfileRepository extends JpaRepository<ApplicationProfile, Long> {
}
