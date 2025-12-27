package com.personal.store.common.domain.support;

import com.personal.store.common.domain.Application;
import com.personal.store.common.domain.ApplicationConfiguration;
import com.personal.store.common.domain.ApplicationImage;
import com.personal.store.common.domain.ApplicationImageType;
import com.personal.store.common.domain.ApplicationProfile;
import com.personal.store.common.domain.ApplicationStage;
import com.personal.store.common.domain.ApplicationVersion;
import com.personal.store.common.domain.TerminalConfiguration;
import com.personal.store.common.domain.TerminalIntegrationType;
import com.personal.store.common.domain.TerminalModel;
import com.personal.store.common.domain.abstraction.StorageObjectStatus;

import jakarta.persistence.EntityManager;

public final class TestEntityFactory {
    private TestEntityFactory() {
    }

    public static Application persistApplication(EntityManager entityManager, String name) {
        Application app = new Application();
        app.setName(name);
        entityManager.persist(app);
        entityManager.flush();
        return app;
    }

    public record AppTerminalConfig(
            Application application,
            TerminalModel terminalModel,
            TerminalConfiguration terminalConfiguration,
            ApplicationConfiguration applicationConfiguration
    ) {
    }

    public static AppTerminalConfig persistAppTerminalConfig(
            EntityManager entityManager,
            String applicationName,
            String terminalModelName,
            TerminalIntegrationType integrationType,
            String packageName
    ) {
        Application app = new Application();
        app.setName(applicationName);
        entityManager.persist(app);

        TerminalModel terminalModel = new TerminalModel();
        terminalModel.setName(terminalModelName);
        entityManager.persist(terminalModel);

        TerminalConfiguration terminalConfiguration = new TerminalConfiguration();
        terminalConfiguration.setTerminalModel(terminalModel);
        terminalConfiguration.setIntegrationType(integrationType);
        entityManager.persist(terminalConfiguration);

        ApplicationConfiguration config = new ApplicationConfiguration();
        config.setApplication(app);
        config.setTerminalConfiguration(terminalConfiguration);
        config.setPackageName(packageName);
        entityManager.persist(config);

        entityManager.flush();

        return new AppTerminalConfig(app, terminalModel, terminalConfiguration, config);
    }

    public static ApplicationImage persistIcon(EntityManager entityManager, Application application) {
        ApplicationImage icon = new ApplicationImage();
        icon.setApplication(application);
        icon.setImageType(ApplicationImageType.ICON);
        icon.setName("icon.png");
        icon.setMimeType("image/png");
        icon.setSizeInBytes(12345L);
        icon.setFileHash(nonZeroHash32());
        icon.setStatus(StorageObjectStatus.AVAILABLE);
        entityManager.persist(icon);
        entityManager.flush();
        return icon;
    }

    public static ApplicationProfile persistProfile(
            EntityManager entityManager,
            ApplicationImage icon,
            String name,
            ApplicationStage stage
    ) {
        ApplicationProfile profile = new ApplicationProfile();
        profile.setName(name);
        profile.setStage(stage);
        profile.setIcon(icon);
        entityManager.persist(profile);
        entityManager.flush();
        return profile;
    }

    public static ApplicationVersion persistVersion(
            EntityManager entityManager,
            ApplicationConfiguration applicationConfiguration,
            String name,
            String versionName,
            long versionCode,
            long size
    ) {
        ApplicationVersion version = new ApplicationVersion();
        version.setApplicationConfiguration(applicationConfiguration);
        version.setName(name);
        version.setVersionName(versionName);
        version.setVersionCode(versionCode);
        version.setSize(size);
        entityManager.persist(version);
        entityManager.flush();
        return version;
    }

    public static byte[] nonZeroHash32() {
        byte[] hash = new byte[32];
        for (int i = 0; i < hash.length; i++) {
            hash[i] = (byte) (i + 1);
        }
        return hash;
    }
}
