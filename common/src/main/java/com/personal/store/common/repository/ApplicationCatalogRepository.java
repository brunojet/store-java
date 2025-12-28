package com.personal.store.common.repository;

import com.personal.store.common.domain.ApplicationCatalog;
import com.personal.store.common.domain.ApplicationCatalogId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationCatalogRepository extends JpaRepository<ApplicationCatalog, ApplicationCatalogId> {
}
