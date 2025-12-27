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
        Application app = new Application();
        app.setName("App Catalog Test");
        entityManager.persist(app);

        TerminalModel terminalModel = new TerminalModel();
        terminalModel.setName("Model X");
        entityManager.persist(terminalModel);

        TerminalConfiguration terminalConfiguration = new TerminalConfiguration();
        terminalConfiguration.setTerminalModel(terminalModel);
        terminalConfiguration.setIntegrationType(TerminalIntegrationType.RFAL);
        entityManager.persist(terminalConfiguration);

        ApplicationConfiguration config = new ApplicationConfiguration();
        config.setApplication(app);
        config.setTerminalConfiguration(terminalConfiguration);
        config.setPackageName("com.personal.store.app");
        entityManager.persist(config);
        entityManager.flush();

        ApplicationCatalog catalog = new ApplicationCatalog();
        catalog.setId(new ApplicationCatalogId(
            new ApplicationConfigurationId(app.getId(), terminalConfiguration.getId()),
            ApplicationStage.PILOT
        ));
        catalog.setApplicationConfiguration(config);
        catalog.setApplicationProfileId(999L);
        catalog.setApplicationVersionId(111L);
        catalog.setActive(Boolean.TRUE);

        entityManager.persist(catalog);
        entityManager.flush();
        entityManager.clear();

        ApplicationCatalogId id = new ApplicationCatalogId(
            new ApplicationConfigurationId(app.getId(), terminalConfiguration.getId()),
            ApplicationStage.PILOT
        );
        ApplicationCatalog reloaded = entityManager.find(ApplicationCatalog.class, id);

        assertThat(reloaded).isNotNull();
        assertThat(reloaded.getId()).isNotNull();
        assertThat(reloaded.getId().getApplicationConfigurationId()).isNotNull();
        assertThat(reloaded.getId().getApplicationConfigurationId().getApplicationId()).isEqualTo(app.getId());
        assertThat(reloaded.getId().getApplicationConfigurationId().getTerminalConfigurationId()).isEqualTo(terminalConfiguration.getId());
        assertThat(reloaded.getId().getStage()).isEqualTo(ApplicationStage.PILOT);
        assertThat(reloaded.getApplicationProfileId()).isEqualTo(999L);
        assertThat(reloaded.getApplicationVersionId()).isEqualTo(111L);
        assertThat(reloaded.getActive()).isTrue();

        assertThat(reloaded.getApplicationConfiguration()).isNotNull();
        assertThat(reloaded.getApplicationConfiguration().getId()).isNotNull();
        assertThat(reloaded.getApplicationConfiguration().getId().getApplicationId()).isEqualTo(app.getId());
        assertThat(reloaded.getApplicationConfiguration().getId().getTerminalConfigurationId()).isEqualTo(terminalConfiguration.getId());
    }
}
