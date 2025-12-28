package com.personal.store.common.domain.abstraction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

class StorageObjectStatusTest {

    @Test
    void getCodeAndFromCodeWork() {
        assertThat(StorageObjectStatus.PROCESSING.getCode()).isEqualTo((short) 10);
        assertThat(StorageObjectStatus.AVAILABLE.getCode()).isEqualTo((short) 20);

        assertThat(StorageObjectStatus.fromCode((short) 10)).isEqualTo(StorageObjectStatus.PROCESSING);
        assertThat(StorageObjectStatus.fromCode((short) 20)).isEqualTo(StorageObjectStatus.AVAILABLE);
        assertThat(StorageObjectStatus.fromCode(null)).isNull();
    }

    @Test
    void fromCodeThrowsForUnknown() {
        assertThatThrownBy(() -> StorageObjectStatus.fromCode((short) 99))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unknown StorageObjectStatus code");
    }
}
