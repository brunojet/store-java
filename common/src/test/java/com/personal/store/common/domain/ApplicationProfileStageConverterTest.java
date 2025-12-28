package com.personal.store.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

class ApplicationProfileStageConverterTest {

    private final ApplicationProfileStageConverter converter = new ApplicationProfileStageConverter();

    @Test
    void convertToDatabaseColumnHandlesNullAndValue() {
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
        assertThat(converter.convertToDatabaseColumn(ApplicationProfileStage.PRODUCTION)).isEqualTo((short) 30);
    }

    @Test
    void convertToEntityAttributeHandlesNullAndValue() {
        assertThat(converter.convertToEntityAttribute(null)).isNull();
        assertThat(converter.convertToEntityAttribute((short) 10)).isEqualTo(ApplicationProfileStage.REVIEW);
    }

    @Test
    void convertToEntityAttributeThrowsForUnknownCode() {
        assertThatThrownBy(() -> converter.convertToEntityAttribute((short) 99))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unknown ApplicationProfileStage code");
    }
}
