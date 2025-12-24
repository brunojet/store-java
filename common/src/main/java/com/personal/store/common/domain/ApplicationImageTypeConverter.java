package com.personal.store.common.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ApplicationImageTypeConverter implements AttributeConverter<ApplicationImageType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ApplicationImageType attribute) {
        if (attribute == null) {
            return null;
        }
        return (int) attribute.getCode();
    }

    @Override
    public ApplicationImageType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return ApplicationImageType.fromCode((short) (int) dbData);
    }
}
