package com.personal.store.common.domain;

import com.personal.store.common.domain.abstraction.BaseEntityAbstraction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "terminal_configuration")
@Getter
@Setter
@NoArgsConstructor
public class TerminalConfiguration extends BaseEntityAbstraction {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "terminal_model_id", nullable = false)
    private TerminalModel terminalModel;

    @Column(name = "integration_type", nullable = false)
    @jakarta.persistence.Enumerated(jakarta.persistence.EnumType.ORDINAL)
    private TerminalIntegrationType integrationType;
}
