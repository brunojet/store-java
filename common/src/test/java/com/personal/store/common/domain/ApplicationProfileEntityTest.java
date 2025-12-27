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
class ApplicationProfileEntityTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void persistsApplicationProfileWithIconAndStage() {
        Application app = TestEntityFactory.persistApplication(entityManager, "App for Profile");

        ApplicationImage icon = TestEntityFactory.persistIcon(entityManager, app);

        ApplicationProfile profile = TestEntityFactory.persistProfile(
            entityManager,
            icon,
            "Profile A",
            ApplicationStage.PRODUCTION
        );
        profile.setPartnerName("Partner X");

        entityManager.flush();
        entityManager.clear();

        ApplicationProfile reloaded = entityManager.find(ApplicationProfile.class, profile.getId());
        assertThat(reloaded).isNotNull();
        assertThat(reloaded.getName()).isEqualTo("Profile A");
        assertThat(reloaded.getStage()).isEqualTo(ApplicationStage.PRODUCTION);
        assertThat(reloaded.getIcon()).isNotNull();
        assertThat(reloaded.getIcon().getId()).isEqualTo(icon.getId());
    }
}
