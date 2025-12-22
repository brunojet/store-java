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
class JpaMappingsSmokeTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void persistsTerminalModel() {
        TerminalModel terminalModel = new TerminalModel();
        terminalModel.setName("Model X");
        entityManager.persist(terminalModel);

        TerminalConfiguration terminalConfiguration = new TerminalConfiguration();
        terminalConfiguration.setTerminalModel(terminalModel);
        terminalConfiguration.setIntegrationType(TerminalIntegrationType.RFAL);
        entityManager.persist(terminalConfiguration);

        // persist filter type and filter
        FilterType filterType = new FilterType();
        filterType.setName("Type A");
        entityManager.persist(filterType);

        Filter filter = new Filter();
        filter.setName("Filter One");
        filter.setFilterType(filterType);
        entityManager.persist(filter);

        entityManager.flush();
        entityManager.clear();

        TerminalModel reloaded = entityManager.find(TerminalModel.class, terminalModel.getId());
        assertThat(reloaded).isNotNull();
        assertThat(reloaded.getName()).isEqualTo("Model X");

        TerminalConfiguration reloadedConfiguration = entityManager.find(TerminalConfiguration.class, terminalConfiguration.getId());
        assertThat(reloadedConfiguration).isNotNull();
        assertThat(reloadedConfiguration.getIntegrationType()).isEqualTo(TerminalIntegrationType.RFAL);
        assertThat(reloadedConfiguration.getTerminalModel()).isNotNull();

        FilterType reloadedFilterType = entityManager.find(FilterType.class, filterType.getId());
        assertThat(reloadedFilterType).isNotNull();
        assertThat(reloadedFilterType.getName()).isEqualTo("Type A");

        Filter reloadedFilter = entityManager.find(Filter.class, filter.getId());
        assertThat(reloadedFilter).isNotNull();
        assertThat(reloadedFilter.getName()).isEqualTo("Filter One");
        assertThat(reloadedFilter.getFilterType()).isNotNull();
        assertThat(reloadedFilter.getFilterType().getId()).isEqualTo(filterType.getId());
    }

}

