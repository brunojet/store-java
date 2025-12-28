package com.personal.store.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

class TerminalIntegrationTypeTest {

    @Test
    void getCodeAndFromCodeWork() {
        assertThat(TerminalIntegrationType.RFAL.getCode()).isEqualTo((short) 0);
        assertThat(TerminalIntegrationType.TEF.getCode()).isEqualTo((short) 1);

        assertThat(TerminalIntegrationType.fromCode((short) 0)).isEqualTo(TerminalIntegrationType.RFAL);
        assertThat(TerminalIntegrationType.fromCode((short) 1)).isEqualTo(TerminalIntegrationType.TEF);
        assertThat(TerminalIntegrationType.fromCode(null)).isNull();
    }

    @Test
    void fromCodeThrowsForUnknown() {
        assertThatThrownBy(() -> TerminalIntegrationType.fromCode((short) 99))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unknown TerminalIntegrationType code");
    }
}
