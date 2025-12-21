package com.personal.store.common.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ApplicationImageTypeConverter implements AttributeConverter<ApplicationImageType, Short> {
    @Override
    public Short convertToDatabaseColumn(ApplicationImageType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public ApplicationImageType convertToEntityAttribute(Short dbData) {
        return ApplicationImageType.fromCode(dbData);
    }
}
