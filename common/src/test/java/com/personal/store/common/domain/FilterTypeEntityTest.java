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
class FilterTypeEntityTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void persistsFilterType() {
        FilterType type = new FilterType();
        type.setName("Type A");
        entityManager.persist(type);

        entityManager.flush();
        entityManager.clear();

        FilterType reloaded = entityManager.find(FilterType.class, type.getId());
        assertThat(reloaded).isNotNull();
        assertThat(reloaded.getName()).isEqualTo("Type A");
    }

}
