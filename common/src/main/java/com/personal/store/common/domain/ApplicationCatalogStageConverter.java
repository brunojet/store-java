package com.personal.store.common.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ApplicationCatalogStageConverter implements AttributeConverter<ApplicationCatalogStage, Short> {
    @Override
    public Short convertToDatabaseColumn(ApplicationCatalogStage attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public ApplicationCatalogStage convertToEntityAttribute(Short dbData) {
        if (dbData == null) {
            return null;
        }
        return ApplicationCatalogStage.fromCode(dbData);
    }
}
