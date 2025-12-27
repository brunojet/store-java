package com.personal.store.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.personal.store.common.CommonTestApplication;
import com.personal.store.common.domain.abstraction.StorageObjectStatus;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ContextConfiguration(classes = CommonTestApplication.class)
class ApplicationProfileEntityTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void persistsApplicationProfileWithIconAndStage() {
        Application app = new Application();
        app.setName("App for Profile");
        entityManager.persist(app);
        entityManager.flush();

        ApplicationImage icon = new ApplicationImage();
        icon.setApplication(app);
        icon.setImageType(ApplicationImageType.ICON);
        icon.setName("icon.png");
        icon.setMimeType("image/png");
        icon.setSizeInBytes(12345L);
        icon.setFileHash(nonZeroHash32());
        icon.setStatus(StorageObjectStatus.AVAILABLE);
        entityManager.persist(icon);
        entityManager.flush();

        ApplicationProfile profile = new ApplicationProfile();
        profile.setName("Profile A");
        profile.setStage(ApplicationStage.PRODUCTION);
        profile.setIcon(icon);
        profile.setPartnerName("Partner X");
        entityManager.persist(profile);

        entityManager.flush();
        entityManager.clear();

        ApplicationProfile reloaded = entityManager.find(ApplicationProfile.class, profile.getId());
        assertThat(reloaded).isNotNull();
        assertThat(reloaded.getName()).isEqualTo("Profile A");
        assertThat(reloaded.getStage()).isEqualTo(ApplicationStage.PRODUCTION);
        assertThat(reloaded.getIcon()).isNotNull();
        assertThat(reloaded.getIcon().getId()).isEqualTo(icon.getId());
    }

    private byte[] nonZeroHash32() {
        byte[] hash = new byte[32];
        for (int i = 0; i < hash.length; i++) {
            hash[i] = (byte) (i + 1);
        }
        return hash;
    }

}
