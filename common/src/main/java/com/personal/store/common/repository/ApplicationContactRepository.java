package com.personal.store.common.repository;

import com.personal.store.common.domain.ApplicationContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationContactRepository extends JpaRepository<ApplicationContact, Long> {
}
