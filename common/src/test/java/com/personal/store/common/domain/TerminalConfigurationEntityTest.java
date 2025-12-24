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
class TerminalConfigurationEntityTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void persistsTerminalConfigurationWithModel() {
        TerminalModel model = new TerminalModel();
        model.setName("Model Y");
        entityManager.persist(model);

        TerminalConfiguration config = new TerminalConfiguration();
        config.setTerminalModel(model);
        config.setIntegrationType(TerminalIntegrationType.RFAL);
        entityManager.persist(config);

        entityManager.flush();
        entityManager.clear();

        TerminalConfiguration reloaded = entityManager.find(TerminalConfiguration.class, config.getId());
        assertThat(reloaded).isNotNull();
        assertThat(reloaded.getIntegrationType()).isEqualTo(TerminalIntegrationType.RFAL);
        assertThat(reloaded.getTerminalModel()).isNotNull();
    }

}
