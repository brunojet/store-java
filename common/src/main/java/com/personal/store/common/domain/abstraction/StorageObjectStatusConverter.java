package com.personal.store.common.domain.abstraction;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class StorageObjectStatusConverter implements AttributeConverter<StorageObjectStatus, Short> {
    @Override
    public Short convertToDatabaseColumn(StorageObjectStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public StorageObjectStatus convertToEntityAttribute(Short dbData) {
        if (dbData == null) {
            return null;
        }
        return StorageObjectStatus.fromCode(dbData);
    }
}
