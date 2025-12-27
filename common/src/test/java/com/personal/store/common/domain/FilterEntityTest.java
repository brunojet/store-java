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
class FilterEntityTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void persistsFilterWithFilterType() {
        FilterType type = new FilterType();
        type.setName("Type B");
        entityManager.persist(type);

        Filter filter = new Filter();
        filter.setName("Filter One");
        filter.setFilterType(type);
        entityManager.persist(filter);

        entityManager.flush();
        entityManager.clear();

        Filter reloaded = entityManager.find(Filter.class, filter.getId());
        assertThat(reloaded).isNotNull();
        assertThat(reloaded.getName()).isEqualTo("Filter One");
        assertThat(reloaded.getFilterType()).isNotNull();
        assertThat(reloaded.getFilterType().getId()).isEqualTo(type.getId());
    }

}
