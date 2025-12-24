package com.personal.store.common.domain.abstraction;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class StorageObjectStatusConverter implements AttributeConverter<StorageObjectStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(StorageObjectStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return (int) attribute.getCode();
    }

    @Override
    public StorageObjectStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return StorageObjectStatus.fromCode((short) (int) dbData);
    }
}
