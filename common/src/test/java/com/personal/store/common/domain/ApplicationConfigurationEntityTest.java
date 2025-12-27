package com.personal.store.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.personal.store.common.CommonTestApplication;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ContextConfiguration(classes = CommonTestApplication.class)
class ApplicationConfigurationEntityTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void persistsAndLoadsApplicationConfigurationByCompositeId() {
        ApplicationConfiguration config = new ApplicationConfiguration();
        config.setApplicationId(10L);
        config.setTerminalConfigurationId(20L);
        config.setPackageName("com.personal.store.app");

        entityManager.persist(config);
        entityManager.flush();
        entityManager.clear();

        ApplicationConfigurationId id = new ApplicationConfigurationId(10L, 20L);
        ApplicationConfiguration reloaded = entityManager.find(ApplicationConfiguration.class, id);

        assertThat(reloaded).isNotNull();
        assertThat(reloaded.getApplicationId()).isEqualTo(10L);
        assertThat(reloaded.getTerminalConfigurationId()).isEqualTo(20L);
        assertThat(reloaded.getPackageName()).isEqualTo("com.personal.store.app");
    }
}
