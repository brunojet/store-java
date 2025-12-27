package com.personal.store.common.repository;

import com.personal.store.common.domain.ApplicationImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationImageRepository extends JpaRepository<ApplicationImage, Long> {
}