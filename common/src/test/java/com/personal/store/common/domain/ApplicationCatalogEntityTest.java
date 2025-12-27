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
class ApplicationCatalogEntityTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void persistsAndLoadsApplicationCatalogByCompositeId() {
        ApplicationConfiguration config = new ApplicationConfiguration();
        config.setApplicationId(10L);
        config.setTerminalConfigurationId(20L);
        config.setPackageName("com.personal.store.app");
        entityManager.persist(config);
        entityManager.flush();

        ApplicationCatalog catalog = new ApplicationCatalog();
        catalog.setApplicationId(10L);
        catalog.setTerminalConfigurationId(20L);
        catalog.setStage(ApplicationStage.PILOT);
        catalog.setApplicationConfiguration(config);
        catalog.setApplicationProfileId(999L);
        catalog.setApplicationVersionId(111L);
        catalog.setActive(Boolean.TRUE);

        entityManager.persist(catalog);
        entityManager.flush();
        entityManager.clear();

        ApplicationCatalogId id = new ApplicationCatalogId(10L, 20L, ApplicationStage.PILOT);
        ApplicationCatalog reloaded = entityManager.find(ApplicationCatalog.class, id);

        assertThat(reloaded).isNotNull();
        assertThat(reloaded.getApplicationId()).isEqualTo(10L);
        assertThat(reloaded.getTerminalConfigurationId()).isEqualTo(20L);
        assertThat(reloaded.getStage()).isEqualTo(ApplicationStage.PILOT);
        assertThat(reloaded.getApplicationProfileId()).isEqualTo(999L);
        assertThat(reloaded.getApplicationVersionId()).isEqualTo(111L);
        assertThat(reloaded.getActive()).isTrue();

        assertThat(reloaded.getApplicationConfiguration()).isNotNull();
        assertThat(reloaded.getApplicationConfiguration().getApplicationId()).isEqualTo(10L);
        assertThat(reloaded.getApplicationConfiguration().getTerminalConfigurationId()).isEqualTo(20L);
    }
}
