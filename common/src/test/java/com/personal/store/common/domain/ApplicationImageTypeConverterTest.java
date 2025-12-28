package com.personal.store.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

class ApplicationImageTypeConverterTest {

    private final ApplicationImageTypeConverter converter = new ApplicationImageTypeConverter();

    @Test
    void convertToDatabaseColumnHandlesNullAndValue() {
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
        assertThat(converter.convertToDatabaseColumn(ApplicationImageType.ICON)).isEqualTo((short) 0);
    }

    @Test
    void convertToEntityAttributeHandlesNullAndValue() {
        assertThat(converter.convertToEntityAttribute(null)).isNull();
        assertThat(converter.convertToEntityAttribute((short) 2)).isEqualTo(ApplicationImageType.BANNER);
    }

    @Test
    void convertToEntityAttributeThrowsForUnknownCode() {
        assertThatThrownBy(() -> converter.convertToEntityAttribute((short) 99))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unknown ApplicationImageType code");
    }
}
