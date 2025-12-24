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
class TerminalModelEntityTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void persistsTerminalModel() {
        TerminalModel model = new TerminalModel();
        model.setName("Model X");
        entityManager.persist(model);

        entityManager.flush();
        entityManager.clear();

        TerminalModel reloaded = entityManager.find(TerminalModel.class, model.getId());
        assertThat(reloaded).isNotNull();
        assertThat(reloaded.getName()).isEqualTo("Model X");
    }

}
