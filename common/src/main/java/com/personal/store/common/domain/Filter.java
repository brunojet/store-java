package com.personal.store.common.domain;

import com.personal.store.common.domain.abstraction.SimpleEntityAbstraction;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "filter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filter extends SimpleEntityAbstraction {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_type_id", nullable = false)
    private FilterType filterType;
}
