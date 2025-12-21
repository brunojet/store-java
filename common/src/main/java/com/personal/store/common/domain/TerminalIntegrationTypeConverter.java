package com.personal.store.common.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TerminalIntegrationTypeConverter implements AttributeConverter<TerminalIntegrationType, Short> {
    @Override
    public Short convertToDatabaseColumn(TerminalIntegrationType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public TerminalIntegrationType convertToEntityAttribute(Short dbData) {
        return TerminalIntegrationType.fromCode(dbData);
    }
}
