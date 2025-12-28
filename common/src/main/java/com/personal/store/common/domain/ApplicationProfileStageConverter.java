package com.personal.store.common.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ApplicationProfileStageConverter implements AttributeConverter<ApplicationProfileStage, Short> {
    @Override
    public Short convertToDatabaseColumn(ApplicationProfileStage attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public ApplicationProfileStage convertToEntityAttribute(Short dbData) {
        if (dbData == null) {
            return null;
        }
        return ApplicationProfileStage.fromCode(dbData);
    }
}
