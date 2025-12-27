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
class ApplicationEntityTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void persistsApplication() {
        Application app = new Application();
        app.setName("My App");
        entityManager.persist(app);

        entityManager.flush();
        entityManager.clear();

        Application reloaded = entityManager.find(Application.class, app.getId());
        assertThat(reloaded).isNotNull();
        assertThat(reloaded.getName()).isEqualTo("My App");
    }

}
