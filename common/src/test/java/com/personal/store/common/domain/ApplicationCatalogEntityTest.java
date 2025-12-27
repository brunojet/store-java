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
class ApplicationCatalogEntityTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void persistsAndLoadsApplicationCatalogByCompositeId() {
        Application app = new Application();
        app.setName("App Catalog Test");
        entityManager.persist(app);

        TerminalModel terminalModel = new TerminalModel();
        terminalModel.setName("Model X");
        entityManager.persist(terminalModel);

        TerminalConfiguration terminalConfiguration = new TerminalConfiguration();
        terminalConfiguration.setTerminalModel(terminalModel);
        terminalConfiguration.setIntegrationType(TerminalIntegrationType.RFAL);
        entityManager.persist(terminalConfiguration);

        ApplicationConfiguration config = new ApplicationConfiguration();
        config.setApplication(app);
        config.setTerminalConfiguration(terminalConfiguration);
        config.setPackageName("com.personal.store.app");
        entityManager.persist(config);
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

        ApplicationProfile profile = new ApplicationProfile();
        profile.setName("Profile A");
        profile.setStage(ApplicationStage.PILOT);
        profile.setIcon(icon);
        entityManager.persist(profile);

        ApplicationVersion version = new ApplicationVersion();
        version.setApplicationConfiguration(config);
        version.setName("Version 1");
        version.setVersionName("1.0.0");
        version.setVersionCode(1L);
        version.setSize(123456L);
        entityManager.persist(version);

        ApplicationCatalog catalog = new ApplicationCatalog();
        catalog.setId(new ApplicationCatalogId(app.getId(), terminalConfiguration.getId(),
                ApplicationStage.PILOT));
        catalog.setApplicationConfiguration(config);
        catalog.setApplicationProfile(profile);
        catalog.setApplicationVersion(version);
        catalog.setActive(Boolean.TRUE);

        entityManager.persist(catalog);
        entityManager.flush();
        entityManager.clear();

        ApplicationCatalogId id = new ApplicationCatalogId(app.getId(), terminalConfiguration.getId(),
                ApplicationStage.PILOT);
        ApplicationCatalog reloaded = entityManager.find(ApplicationCatalog.class, id);

        assertThat(reloaded).isNotNull();
        assertThat(reloaded.getId()).isNotNull();
        assertThat(reloaded.getId().getApplicationId()).isEqualTo(app.getId());
        assertThat(reloaded.getId().getTerminalConfigurationId())
                .isEqualTo(terminalConfiguration.getId());
        assertThat(reloaded.getId().getStage()).isEqualTo(ApplicationStage.PILOT);
        assertThat(reloaded.getApplicationProfile()).isNotNull();
        assertThat(reloaded.getApplicationProfile().getId()).isEqualTo(profile.getId());
        assertThat(reloaded.getApplicationVersion()).isNotNull();
        assertThat(reloaded.getApplicationVersion().getId()).isEqualTo(version.getId());
        assertThat(reloaded.getActive()).isTrue();

        assertThat(reloaded.getApplicationConfiguration()).isNotNull();
        assertThat(reloaded.getApplicationConfiguration().getId()).isNotNull();
        assertThat(reloaded.getApplicationConfiguration().getId().getApplicationId()).isEqualTo(app.getId());
        assertThat(reloaded.getApplicationConfiguration().getId().getTerminalConfigurationId())
                .isEqualTo(terminalConfiguration.getId());
    }

        private byte[] nonZeroHash32() {
                byte[] hash = new byte[32];
                for (int i = 0; i < hash.length; i++) {
                        hash[i] = (byte) (i + 1);
                }
                return hash;
        }
}
