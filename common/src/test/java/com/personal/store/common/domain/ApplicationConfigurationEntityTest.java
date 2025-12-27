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
class ApplicationConfigurationEntityTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void persistsAndLoadsApplicationConfigurationByCompositeId() {
        TestEntityFactory.AppTerminalConfig ctx = TestEntityFactory.persistAppTerminalConfig(
            entityManager,
            "App Config Test",
            "Model X",
            TerminalIntegrationType.RFAL,
            "com.personal.store.app"
        );

        Application app = ctx.application();
        TerminalConfiguration terminalConfiguration = ctx.terminalConfiguration();

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
