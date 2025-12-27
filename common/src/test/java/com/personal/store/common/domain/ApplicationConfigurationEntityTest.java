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
        Application app = new Application();
        app.setName("App Config Test");
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
        entityManager.clear();

        ApplicationConfigurationId id = new ApplicationConfigurationId(app.getId(), terminalConfiguration.getId());
        ApplicationConfiguration reloaded = entityManager.find(ApplicationConfiguration.class, id);

        assertThat(reloaded).isNotNull();
        assertThat(reloaded.getId()).isNotNull();
        assertThat(reloaded.getId().getApplicationId()).isEqualTo(app.getId());
        assertThat(reloaded.getId().getTerminalConfigurationId()).isEqualTo(terminalConfiguration.getId());
        assertThat(reloaded.getPackageName()).isEqualTo("com.personal.store.app");

        assertThat(reloaded.getApplication()).isNotNull();
        assertThat(reloaded.getApplication().getId()).isEqualTo(app.getId());
        assertThat(reloaded.getTerminalConfiguration()).isNotNull();
        assertThat(reloaded.getTerminalConfiguration().getId()).isEqualTo(terminalConfiguration.getId());
    }
}
