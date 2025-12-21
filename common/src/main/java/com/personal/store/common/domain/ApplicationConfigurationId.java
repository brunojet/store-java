package com.personal.store.common.domain;

import java.io.Serializable;
import java.util.Objects;

public class ApplicationConfigurationId implements Serializable {
    private Long applicationId;
    private Long integrationTypeId;
    private Long terminalModelId;

    public ApplicationConfigurationId() {}

    public ApplicationConfigurationId(Long a, Long i, Long t) { this.applicationId = a; this.integrationTypeId = i; this.terminalModelId = t; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationConfigurationId that = (ApplicationConfigurationId) o;
        return Objects.equals(applicationId, that.applicationId) && Objects.equals(integrationTypeId, that.integrationTypeId) && Objects.equals(terminalModelId, that.terminalModelId);
    }

    @Override
    public int hashCode() { return Objects.hash(applicationId, integrationTypeId, terminalModelId); }
}
