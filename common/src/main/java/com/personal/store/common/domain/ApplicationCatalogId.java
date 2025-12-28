package com.personal.store.common.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ApplicationCatalogId implements Serializable {
    @Column(name = "application_id", insertable = false, updatable = false)
    private Long applicationId;

    @Column(name = "terminal_configuration_id", insertable = false, updatable = false)
    private Long terminalConfigurationId;

    @Column(name = "stage")
    @Convert(converter = ApplicationCatalogStageConverter.class)
    private ApplicationCatalogStage stage;
}
