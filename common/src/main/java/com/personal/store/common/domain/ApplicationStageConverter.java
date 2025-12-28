package com.personal.store.common.domain;

import java.util.Set;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ApplicationStageConverter implements AttributeConverter<ApplicationStage, Short> {
    @Override
    public Short convertToDatabaseColumn(ApplicationStage attribute) {
        if (attribute == null) {
            return null;
        }
        if (!isHibernateEnumCheckConstraintRendering()) {
            validateAllowed(attribute);
        }
        return attribute.getCode();
    }

    @Override
    public ApplicationStage convertToEntityAttribute(Short dbData) {
        if (dbData == null) {
            return null;
        }
        ApplicationStage stage = ApplicationStage.fromCode(dbData);
        validateAllowed(stage);
        return stage;
    }

    protected Set<ApplicationStage> allowedStages() {
        return null;
    }

    protected String converterContext() {
        return getClass().getSimpleName();
    }

    private void validateAllowed(ApplicationStage stage) {
        Set<ApplicationStage> allowed = allowedStages();
        if (allowed == null) {
            return;
        }
        if (!allowed.contains(stage)) {
            throw new IllegalArgumentException(
                    "Stage " + stage + " is not allowed for " + converterContext() + ". Allowed: " + allowed
            );
        }
    }

    private boolean isHibernateEnumCheckConstraintRendering() {
        // Hibernate calls AttributeConverter#convertToDatabaseColumn for *all* enum values while generating
        // check constraints (EnumJavaType.renderConvertedEnumCheckConstraint). We must not reject values
        // there, otherwise the SessionFactory can't bootstrap.
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if ("org.hibernate.type.descriptor.java.EnumJavaType".equals(element.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
