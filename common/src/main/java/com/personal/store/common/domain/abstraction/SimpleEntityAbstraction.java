package com.personal.store.common.domain.abstraction;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class SimpleEntityAbstraction extends BaseEntityAbstraction {
    @Column(name = "name", nullable = false, length = 60)
    private String name;

    @Column(name = "description", length = 500)
    private String description;
}
