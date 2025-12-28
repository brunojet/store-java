package com.personal.store.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

class ApplicationVersionStageTest {

    @Test
    void getCodeAndFromCodeWork() {
        assertThat(ApplicationVersionStage.PILOT.getCode()).isEqualTo((short) 20);
        assertThat(ApplicationVersionStage.PRODUCTION.getCode()).isEqualTo((short) 30);

        assertThat(ApplicationVersionStage.fromCode((short) 20)).isEqualTo(ApplicationVersionStage.PILOT);
        assertThat(ApplicationVersionStage.fromCode((short) 30)).isEqualTo(ApplicationVersionStage.PRODUCTION);
        assertThat(ApplicationVersionStage.fromCode(null)).isNull();
    }

    @Test
    void fromCodeThrowsForUnknown() {
        assertThatThrownBy(() -> ApplicationVersionStage.fromCode((short) 99))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unknown ApplicationVersionStage code");
    }
}
