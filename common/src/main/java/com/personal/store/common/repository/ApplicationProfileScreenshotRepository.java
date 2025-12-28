package com.personal.store.common.repository;

import com.personal.store.common.domain.ApplicationProfileScreenshot;
import com.personal.store.common.domain.ApplicationProfileScreenshotId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationProfileScreenshotRepository extends JpaRepository<ApplicationProfileScreenshot, ApplicationProfileScreenshotId> {
}
