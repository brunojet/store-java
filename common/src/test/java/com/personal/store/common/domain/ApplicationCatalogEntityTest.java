package com.personal.store.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.personal.store.common.CommonTestApplication;
import com.personal.store.common.domain.support.TestEntityFactory;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ContextConfiguration(classes = CommonTestApplication.class)
class ApplicationCatalogEntityTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void persistsAndLoadsApplicationCatalogByCompositeId() {
        TestEntityFactory.AppTerminalConfig ctx = TestEntityFactory.persistAppTerminalConfig(
                entityManager,
                "App Catalog Test",
                "Model X",
                TerminalIntegrationType.RFAL,
                "com.personal.store.app"
        );

        Application app = ctx.application();
        TerminalConfiguration terminalConfiguration = ctx.terminalConfiguration();
        ApplicationConfiguration config = ctx.applicationConfiguration();

        ApplicationImage icon = TestEntityFactory.persistIcon(entityManager, app);
        ApplicationProfile profile = TestEntityFactory.persistProfile(
                entityManager,
                icon,
                "Profile A",
                ApplicationProfileStage.REVIEW
        );
        ApplicationVersion version = TestEntityFactory.persistVersion(
                entityManager,
                config,
                "Version 1",
                "1.0.0",
                1L,
                123456L
        );

        ApplicationCatalog catalog = new ApplicationCatalog();
        catalog.setId(new ApplicationCatalogId(app.getId(), terminalConfiguration.getId(),
                ApplicationCatalogStage.PILOT));
        catalog.setApplicationConfiguration(config);
        catalog.setApplicationProfile(profile);
        catalog.setApplicationVersion(version);
        catalog.setActive(Boolean.TRUE);

        entityManager.persist(catalog);
        entityManager.flush();
        entityManager.clear();

        ApplicationCatalogId id = new ApplicationCatalogId(app.getId(), terminalConfiguration.getId(),
                ApplicationCatalogStage.PILOT);
        ApplicationCatalog reloaded = entityManager.find(ApplicationCatalog.class, id);

        assertThat(reloaded).isNotNull();
        assertThat(reloaded.getId()).isNotNull();
        assertThat(reloaded.getId().getApplicationId()).isEqualTo(app.getId());
        assertThat(reloaded.getId().getTerminalConfigurationId())
                .isEqualTo(terminalConfiguration.getId());
        assertThat(reloaded.getId().getStage()).isEqualTo(ApplicationCatalogStage.PILOT);
        assertThat(reloaded.getApplicationProfile()).isNotNull();
        assertThat(reloaded.getApplicationProfile().getId()).isEqualTo(profile.getId());
        assertThat(reloaded.getApplicationVersion()).isNotNull();
        assertThat(reloaded.getApplicationVersion().getId()).isEqualTo(version.getId());
        assertThat(reloaded.getActive()).isTrue();

        assertThat(reloaded.getApplicationConfiguration()).isNotNull();
        assertThat(reloaded.getApplicationConfiguration().getId()).isNotNull();
        assertThat(reloaded.getApplicationConfiguration().getId().getApplicationId()).isEqualTo(app.getId());
        assertThat(reloaded.getApplicationConfiguration().getId().getTerminalConfigurationId())
                .isEqualTo(terminalConfiguration.getId());
    }
}
