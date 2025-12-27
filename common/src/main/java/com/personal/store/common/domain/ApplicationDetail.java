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
@Table(name = "application_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDetail extends BaseEntityAbstraction {
    @Column(name = "description", length = 255)
    private String description;
}
