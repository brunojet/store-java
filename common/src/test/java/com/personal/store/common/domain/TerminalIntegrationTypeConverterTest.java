package com.personal.store.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

class TerminalIntegrationTypeConverterTest {

    private final TerminalIntegrationTypeConverter converter = new TerminalIntegrationTypeConverter();

    @Test
    void convertToDatabaseColumnHandlesNullAndValue() {
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
        assertThat(converter.convertToDatabaseColumn(TerminalIntegrationType.TEF)).isEqualTo((short) 1);
    }

    @Test
    void convertToEntityAttributeHandlesNullAndValue() {
        assertThat(converter.convertToEntityAttribute(null)).isNull();
        assertThat(converter.convertToEntityAttribute((short) 0)).isEqualTo(TerminalIntegrationType.RFAL);
    }

    @Test
    void convertToEntityAttributeThrowsForUnknownCode() {
        assertThatThrownBy(() -> converter.convertToEntityAttribute((short) 99))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unknown TerminalIntegrationType code");
    }
}
