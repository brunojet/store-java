package com.personal.store.common.domain;

import com.personal.store.common.domain.abstraction.BaseEntityAbstraction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "category_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryType extends BaseEntityAbstraction {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "longtext")
    private String description;

    @Column(name = "active")
    private Boolean active;
}
