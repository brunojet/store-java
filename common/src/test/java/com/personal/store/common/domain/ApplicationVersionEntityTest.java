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
class ApplicationVersionEntityTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void persistsAndLoadsApplicationVersionWithCompositeFk() {
        TestEntityFactory.AppTerminalConfig ctx = TestEntityFactory.persistAppTerminalConfig(
            entityManager,
            "App Version Test",
            "Model X",
            TerminalIntegrationType.RFAL,
            "com.personal.store.app"
        );

        Application app = ctx.application();
        TerminalConfiguration terminalConfiguration = ctx.terminalConfiguration();
        ApplicationConfiguration config = ctx.applicationConfiguration();

        ApplicationVersion version = new ApplicationVersion();
        version.setApplicationConfiguration(config);
        version.setName("v1");
        version.setDescription("first version");
        version.setVersionName("1.0.0");
        version.setVersionCode(1L);
        version.setSize(123456L);

        entityManager.persist(version);
        entityManager.flush();

        entityManager.clear();

        ApplicationVersion reloaded = entityManager.find(ApplicationVersion.class, version.getId());

        assertThat(reloaded).isNotNull();
        assertThat(reloaded.getName()).isEqualTo("v1");
        assertThat(reloaded.getVersionName()).isEqualTo("1.0.0");
        assertThat(reloaded.getVersionCode()).isEqualTo(1L);
        assertThat(reloaded.getSize()).isEqualTo(123456L);

        assertThat(reloaded.getApplicationConfiguration()).isNotNull();
        assertThat(reloaded.getApplicationConfiguration().getId()).isNotNull();
        assertThat(reloaded.getApplicationConfiguration().getId().getApplicationId()).isEqualTo(app.getId());
        assertThat(reloaded.getApplicationConfiguration().getId().getTerminalConfigurationId()).isEqualTo(terminalConfiguration.getId());
        assertThat(reloaded.getApplicationConfiguration().getPackageName()).isEqualTo("com.personal.store.app");
    }
}
