package com.personal.store.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "application_configuration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationConfiguration {
    @EmbeddedId
    private ApplicationConfigurationId id = new ApplicationConfigurationId();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("applicationId")
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    private Application application;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("terminalConfigurationId")
    @JoinColumn(name = "terminal_configuration_id", referencedColumnName = "id")
    private TerminalConfiguration terminalConfiguration;

    @Column(name = "package_name", length = 255)
    private String packageName;
}
