package com.personal.store.common.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ApplicationStageConverter implements AttributeConverter<ApplicationStage, Short> {
    @Override
    public Short convertToDatabaseColumn(ApplicationStage attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public ApplicationStage convertToEntityAttribute(Short dbData) {
        return ApplicationStage.fromCode(dbData);
    }
}
