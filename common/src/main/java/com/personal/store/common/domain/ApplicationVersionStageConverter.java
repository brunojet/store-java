package com.personal.store.common.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ApplicationVersionStageConverter implements AttributeConverter<ApplicationVersionStage, Short> {
    @Override
    public Short convertToDatabaseColumn(ApplicationVersionStage attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public ApplicationVersionStage convertToEntityAttribute(Short dbData) {
        if (dbData == null) {
            return null;
        }
        return ApplicationVersionStage.fromCode(dbData);
    }
}
