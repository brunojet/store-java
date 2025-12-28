package com.personal.store.common.domain.abstraction;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class AuditAbstractionTest {

    private static class TestAuditEntity extends AuditAbstraction {
        // empty concrete subclass for testing protected lifecycle methods
    }

    @Test
    void onCreateDoesNotOverwriteCreatedAtIfAlreadySet() {
        TestAuditEntity entity = new TestAuditEntity();
        LocalDateTime preset = LocalDateTime.of(2000, 1, 1, 0, 0);
        entity.setCreatedAt(preset);

        LocalDateTime before = LocalDateTime.now();
        entity.onCreate();
        LocalDateTime after = LocalDateTime.now();

        assertThat(entity.getCreatedAt()).isEqualTo(preset);
        assertThat(entity.getUpdatedAt()).isNotNull()
            .isAfterOrEqualTo(before)
            .isBeforeOrEqualTo(after);
    }
}
