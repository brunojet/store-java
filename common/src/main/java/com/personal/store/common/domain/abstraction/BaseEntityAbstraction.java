package com.personal.store.common.domain.abstraction;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntityAbstraction extends AuditAbstraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
