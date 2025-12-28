package com.personal.store.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

class ApplicationImageTypeTest {

    @Test
    void getCodeAndFromCodeWork() {
        assertThat(ApplicationImageType.ICON.getCode()).isEqualTo((short) 0);
        assertThat(ApplicationImageType.BANNER.getCode()).isEqualTo((short) 2);

        assertThat(ApplicationImageType.fromCode((short) 0)).isEqualTo(ApplicationImageType.ICON);
        assertThat(ApplicationImageType.fromCode((short) 2)).isEqualTo(ApplicationImageType.BANNER);
        assertThat(ApplicationImageType.fromCode(null)).isNull();
    }

    @Test
    void fromCodeThrowsForUnknown() {
        assertThatThrownBy(() -> ApplicationImageType.fromCode((short) 99))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unknown ApplicationImageType code");
    }
}
