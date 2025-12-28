package com.personal.store.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ApplicationCatalogStageTest {

    @Test
    void getCodeMatchesConstants() {
        assertThat(ApplicationCatalogStage.REVIEW.getCode()).isEqualTo((short) 10);
        assertThat(ApplicationCatalogStage.PILOT.getCode()).isEqualTo((short) 20);
        assertThat(ApplicationCatalogStage.PRODUCTION.getCode()).isEqualTo((short) 30);
    }

    @Test
    void fromCodeReturnsExpected() {
        assertThat(ApplicationCatalogStage.fromCode((short) 10)).isEqualTo(ApplicationCatalogStage.REVIEW);
        assertThat(ApplicationCatalogStage.fromCode((short) 20)).isEqualTo(ApplicationCatalogStage.PILOT);
        assertThat(ApplicationCatalogStage.fromCode((short) 30)).isEqualTo(ApplicationCatalogStage.PRODUCTION);
    }

    @Test
    void fromCodeReturnsNullForNull() {
        assertThat(ApplicationCatalogStage.fromCode(null)).isNull();
    }

    @Test
    void fromCodeThrowsForUnknown() {
        assertThatThrownBy(() -> ApplicationCatalogStage.fromCode((short) 99))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unknown ApplicationCatalogStage code");
    }
}
