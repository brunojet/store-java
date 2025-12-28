package com.personal.store.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

class ApplicationVersionStageConverterTest {

    private final ApplicationVersionStageConverter converter = new ApplicationVersionStageConverter();

    @Test
    void convertToDatabaseColumnHandlesNullAndValue() {
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
        assertThat(converter.convertToDatabaseColumn(ApplicationVersionStage.PILOT)).isEqualTo((short) 20);
    }

    @Test
    void convertToEntityAttributeHandlesNullAndValue() {
        assertThat(converter.convertToEntityAttribute(null)).isNull();
        assertThat(converter.convertToEntityAttribute((short) 30)).isEqualTo(ApplicationVersionStage.PRODUCTION);
    }

    @Test
    void convertToEntityAttributeThrowsForUnknownCode() {
        assertThatThrownBy(() -> converter.convertToEntityAttribute((short) 99))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unknown ApplicationVersionStage code");
    }
}
