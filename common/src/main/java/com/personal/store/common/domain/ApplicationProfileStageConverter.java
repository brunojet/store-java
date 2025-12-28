package com.personal.store.common.domain;

import java.util.EnumSet;
import java.util.Set;

import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ApplicationProfileStageConverter extends ApplicationStageConverter {
    @Override
    protected Set<ApplicationStage> allowedStages() {
        return EnumSet.of(ApplicationStage.PENDING, ApplicationStage.REVIEW, ApplicationStage.PRODUCTION, ApplicationStage.ARCHIVED);
    }
}
