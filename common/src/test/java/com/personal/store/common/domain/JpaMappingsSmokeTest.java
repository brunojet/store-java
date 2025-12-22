package com.personal.store.common.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.personal.store.common.CommonTestApplication;
import com.personal.store.common.domain.abstraction.StorageObjectStatus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ContextConfiguration(classes = CommonTestApplication.class)
class JpaMappingsSmokeTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void persistsAndLoadsProfileWithOrderedScreenshotsAndFilters() {
        Application application = new Application();
        application.setName("App");
        entityManager.persist(application);
        entityManager.flush();

        ApplicationImage icon = newImage(application, "icon.png", ApplicationImageType.ICON, StorageObjectStatus.AVAILABLE);
        entityManager.persist(icon);
        entityManager.flush();

        ApplicationProfile profile = new ApplicationProfile();
        profile.setName("Profile v1");
        profile.setStage(ApplicationStage.PENDING);
        profile.setIcon(icon);
        entityManager.persist(profile);

        ApplicationImage screenshot1 = newImage(application, "s1.png", ApplicationImageType.SCREENSHOT, StorageObjectStatus.AVAILABLE);
        ApplicationImage screenshot2 = newImage(application, "s2.png", ApplicationImageType.SCREENSHOT, StorageObjectStatus.AVAILABLE);
        entityManager.persist(screenshot1);
        entityManager.persist(screenshot2);

        entityManager.flush();

        ApplicationProfileScreenshot s2 = new ApplicationProfileScreenshot(
            profile.getId(),
            screenshot2.getId(),
            profile,
            screenshot2,
            2
        );
        ApplicationProfileScreenshot s1 = new ApplicationProfileScreenshot(
            profile.getId(),
            screenshot1.getId(),
            profile,
            screenshot1,
            1
        );
        entityManager.persist(s2);
        entityManager.persist(s1);

        FilterType filterType = new FilterType();
        filterType.setName("Category");
        entityManager.persist(filterType);

        Filter filter = new Filter();
        filter.setName("NFC");
        filter.setFilterType(filterType);
        entityManager.persist(filter);

        profile.getFilters().add(filter);

        entityManager.flush();
        entityManager.clear();

        ApplicationProfile reloaded = entityManager.find(ApplicationProfile.class, profile.getId());
        assertThat(reloaded).isNotNull();
        assertThat(reloaded.getStage()).isEqualTo(ApplicationStage.PENDING);
        assertThat(reloaded.getIcon()).isNotNull();
        assertThat(reloaded.getIcon().getImageType()).isEqualTo(ApplicationImageType.ICON);
        assertThat(reloaded.getFilters()).hasSize(1);

        List<Integer> positions = reloaded.getScreenshots().stream().map(ApplicationProfileScreenshot::getPosition).toList();
        assertThat(positions).containsExactly(1, 2);
    }

    @Test
    void resolvesCompositeJoinFromCatalogToConfiguration() {
        TerminalModel terminalModel = new TerminalModel();
        terminalModel.setName("Model X");
        entityManager.persist(terminalModel);
        entityManager.flush();

        TerminalConfiguration terminalConfiguration = new TerminalConfiguration();
        terminalConfiguration.setTerminalModel(terminalModel);
        terminalConfiguration.setIntegrationType(TerminalIntegrationType.RFAL);
        entityManager.persist(terminalConfiguration);
        entityManager.flush();

        Application application = new Application();
        application.setName("App");
        entityManager.persist(application);
        entityManager.flush();

        ApplicationConfiguration configuration = new ApplicationConfiguration(
            application.getId(),
            terminalConfiguration.getId(),
            "com.example.app"
        );
        entityManager.persist(configuration);

        ApplicationCatalog catalog = new ApplicationCatalog();
        catalog.setApplicationId(application.getId());
        catalog.setTerminalConfigurationId(terminalConfiguration.getId());
        catalog.setStage(ApplicationStage.PILOT);
        catalog.setApplicationConfiguration(configuration);
        catalog.setActive(true);
        entityManager.persist(catalog);

        entityManager.flush();
        entityManager.clear();

        ApplicationCatalogId id = new ApplicationCatalogId(application.getId(), terminalConfiguration.getId(), ApplicationStage.PILOT);
        ApplicationCatalog reloaded = entityManager.find(ApplicationCatalog.class, id);

        assertThat(reloaded).isNotNull();
        assertThat(reloaded.getStage()).isEqualTo(ApplicationStage.PILOT);
        assertThat(reloaded.getApplicationConfiguration()).isNotNull();
        assertThat(reloaded.getApplicationConfiguration().getPackageName()).isEqualTo("com.example.app");
        assertThat(terminalConfiguration.getIntegrationType()).isEqualTo(TerminalIntegrationType.RFAL);
    }

    private static ApplicationImage newImage(
        Application application,
        String fileName,
        ApplicationImageType imageType,
        StorageObjectStatus status
    ) {
        ApplicationImage image = new ApplicationImage();
        image.setApplication(application);
        image.setName(fileName);
        image.setMimeType("image/png");
        image.setSizeInBytes(123L);
        image.setFileHash(zeroHash());
        image.setStatus(status);
        image.setImageType(imageType);
        return image;
    }

    private static byte[] zeroHash() {
        return new byte[32];
    }
}
