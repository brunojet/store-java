package com.personal.store.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.personal.store.common.CommonTestApplication;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;

@DataJpaTest
@ContextConfiguration(classes = CommonTestApplication.class)
class ApplicationImageEntityTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void persistsApplicationImageWithApplication() {
        Application app = new Application();
        app.setName("App Img Test");
        entityManager.persist(app);
        entityManager.flush();

        // Describe how Hibernate created the table (columns + constraints) in the active DB
        Session session = entityManager.unwrap(Session.class);
        String databaseProduct = session.doReturningWork(c -> c.getMetaData().getDatabaseProductName());
        System.out.println("[schema] DB product = " + databaseProduct);
        System.out.println("[schema] APPLICATION_IMAGE columns:");

        if (databaseProduct != null && databaseProduct.toLowerCase().contains("mysql")) {
            @SuppressWarnings("unchecked")
            java.util.List<Object[]> cols = entityManager.createNativeQuery(
                    "SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE, CHARACTER_MAXIMUM_LENGTH, COLUMN_TYPE "
                            + "FROM INFORMATION_SCHEMA.COLUMNS "
                            + "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='application_image' "
                            + "ORDER BY ORDINAL_POSITION")
                .getResultList();
            for (Object[] r : cols) {
                System.out.println("  " + r[0] + " dataType=" + r[1] + " nullable=" + r[2] + " len=" + r[3] + " columnType=" + r[4]);
            }

            System.out.println("[schema] APPLICATION_IMAGE constraints (MySQL):");
            @SuppressWarnings("unchecked")
            java.util.List<Object[]> constraints = entityManager.createNativeQuery(
                    "SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE "
                            + "FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS "
                            + "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='application_image' "
                            + "ORDER BY CONSTRAINT_TYPE, CONSTRAINT_NAME")
                .getResultList();
            for (Object[] r : constraints) {
                System.out.println("  " + r[0] + " type=" + r[1]);
            }

            // MySQL 8+ exposes CHECK clauses via INFORMATION_SCHEMA.CHECK_CONSTRAINTS (CHECK_CLAUSE)
            System.out.println("[schema] APPLICATION_IMAGE check constraints (MySQL, if supported):");
            try {
                @SuppressWarnings("unchecked")
                java.util.List<Object[]> checks = entityManager.createNativeQuery(
                        "SELECT tc.CONSTRAINT_NAME, cc.CHECK_CLAUSE "
                                + "FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc "
                                + "JOIN INFORMATION_SCHEMA.CHECK_CONSTRAINTS cc "
                                + "  ON cc.CONSTRAINT_SCHEMA = tc.CONSTRAINT_SCHEMA AND cc.CONSTRAINT_NAME = tc.CONSTRAINT_NAME "
                                + "WHERE tc.TABLE_SCHEMA = DATABASE() AND tc.TABLE_NAME='application_image' AND tc.CONSTRAINT_TYPE='CHECK' "
                                + "ORDER BY tc.CONSTRAINT_NAME")
                    .getResultList();
                for (Object[] r : checks) {
                    System.out.println("  " + r[0] + " => " + r[1]);
                }
            } catch (Exception e) {
                System.out.println("  (could not query MySQL CHECK clauses: " + e.getClass().getSimpleName() + ")");
            }
        } else {
            // H2 / others
            @SuppressWarnings("unchecked")
            java.util.List<Object[]> cols = entityManager.createNativeQuery(
                    "SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE, CHARACTER_MAXIMUM_LENGTH "
                            + "FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='APPLICATION_IMAGE' ORDER BY ORDINAL_POSITION")
                .getResultList();
            for (Object[] r : cols) {
                System.out.println("  " + r[0] + " dataType=" + r[1] + " nullable=" + r[2] + " len=" + r[3]);
            }

            System.out.println("[schema] APPLICATION_IMAGE check constraints (H2):");
            @SuppressWarnings("unchecked")
            java.util.List<Object[]> checks = entityManager.createNativeQuery(
                    "SELECT CONSTRAINT_NAME, CHECK_EXPRESSION "
                            + "FROM INFORMATION_SCHEMA.CHECK_CONSTRAINTS WHERE TABLE_NAME='APPLICATION_IMAGE' ORDER BY CONSTRAINT_NAME")
                .getResultList();
            for (Object[] r : checks) {
                System.out.println("  " + r[0] + " => " + r[1]);
            }
        }

        ApplicationImage img = new ApplicationImage();
        img.setApplication(app);
        img.setImageType(ApplicationImageType.ICON);
        img.setName("icon.png");
        img.setMimeType("image/png");
        img.setSizeInBytes(12345L);
        // use a non-zero fileHash to avoid any DB check that might reject all-zero values
        byte[] hash = new byte[32];
        for (int i = 0; i < hash.length; i++) {
            hash[i] = (byte) (i + 1);
        }
        img.setFileHash(hash);
        img.setStatus(com.personal.store.common.domain.abstraction.StorageObjectStatus.AVAILABLE);

        System.out.println("[test] insert values:");
        System.out.println("  imageType = " + img.getImageType());
        System.out.println("  status = " + img.getStatus());
        System.out.println("  fileHash.length = " + img.getFileHash().length);
        entityManager.persist(img);

        entityManager.flush();
        entityManager.clear();

        ApplicationImage reloaded = entityManager.find(ApplicationImage.class, img.getId());
        assertThat(reloaded).isNotNull();
        assertThat(reloaded.getImageType()).isEqualTo(ApplicationImageType.ICON);
        assertThat(reloaded.getApplication()).isNotNull();
        assertThat(reloaded.getApplication().getId()).isEqualTo(app.getId());
    }

}
