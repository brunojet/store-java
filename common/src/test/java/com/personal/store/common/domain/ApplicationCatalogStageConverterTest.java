package com.personal.store.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ApplicationCatalogStageConverterTest {

    private final ApplicationCatalogStageConverter converter = new ApplicationCatalogStageConverter();

    @Test
    void convertToDatabaseColumnHandlesNullAndValue() {
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
        assertThat(converter.convertToDatabaseColumn(ApplicationCatalogStage.PILOT)).isEqualTo((short) 20);
    }

    @Test
    void convertToEntityAttributeHandlesNullAndValue() {
        assertThat(converter.convertToEntityAttribute(null)).isNull();
        assertThat(converter.convertToEntityAttribute((short) 10)).isEqualTo(ApplicationCatalogStage.REVIEW);
    }

    @Test
    void convertToEntityAttributeThrowsForUnknownCode() {
        assertThatThrownBy(() -> converter.convertToEntityAttribute((short) 99))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unknown ApplicationCatalogStage code");
    }
}
