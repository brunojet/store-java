package com.personal.store.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

class ApplicationProfileStageTest {

    @Test
    void getCodeAndFromCodeWork() {
        assertThat(ApplicationProfileStage.REVIEW.getCode()).isEqualTo((short) 10);
        assertThat(ApplicationProfileStage.PRODUCTION.getCode()).isEqualTo((short) 30);

        assertThat(ApplicationProfileStage.fromCode((short) 10)).isEqualTo(ApplicationProfileStage.REVIEW);
        assertThat(ApplicationProfileStage.fromCode((short) 30)).isEqualTo(ApplicationProfileStage.PRODUCTION);
        assertThat(ApplicationProfileStage.fromCode(null)).isNull();
    }

    @Test
    void fromCodeThrowsForUnknown() {
        assertThatThrownBy(() -> ApplicationProfileStage.fromCode((short) 99))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unknown ApplicationProfileStage code");
    }
}
