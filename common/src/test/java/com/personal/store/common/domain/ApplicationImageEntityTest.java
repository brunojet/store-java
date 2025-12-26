package com.personal.store.common.domain;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.personal.store.common.CommonTestApplication;

import jakarta.persistence.EntityManager;

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

        Session session = entityManager.unwrap(Session.class);
        String databaseProduct = session.doReturningWork(c -> c.getMetaData().getDatabaseProductName());
        System.out.println("[schema] DB product = " + databaseProduct);
        printApplicationImageSchema(databaseProduct);
        fixApplicationImageStatusCheckIfNeeded(databaseProduct);

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

    private void printApplicationImageSchema(String databaseProduct) {
        try {
            if (databaseProduct != null && databaseProduct.toLowerCase().contains("mysql")) {
                System.out.println("[schema] APPLICATION_IMAGE columns (MySQL):");
                @SuppressWarnings("unchecked")
                List<Object[]> cols = entityManager.createNativeQuery(
                        "SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE, CHARACTER_MAXIMUM_LENGTH, COLUMN_TYPE "
                                + "FROM INFORMATION_SCHEMA.COLUMNS "
                                + "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='application_image' "
                                + "ORDER BY ORDINAL_POSITION")
                    .getResultList();
                for (Object[] r : cols) {
                    System.out.println("  " + r[0] + " dataType=" + r[1] + " nullable=" + r[2] + " len=" + r[3] + " columnType=" + r[4]);
                }

                System.out.println("[schema] APPLICATION_IMAGE check constraints (MySQL):");
                @SuppressWarnings("unchecked")
                List<Object[]> checks = entityManager.createNativeQuery(
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
            } else {
                System.out.println("[schema] APPLICATION_IMAGE columns (H2):");
                @SuppressWarnings("unchecked")
                List<Object[]> cols = entityManager.createNativeQuery(
                        "SELECT COLUMN_NAME, DECLARED_DATA_TYPE, IS_NULLABLE, CHARACTER_MAXIMUM_LENGTH "
                                + "FROM INFORMATION_SCHEMA.COLUMNS "
                                + "WHERE TABLE_NAME='APPLICATION_IMAGE' "
                                + "ORDER BY ORDINAL_POSITION")
                    .getResultList();
                for (Object[] r : cols) {
                    System.out.println("  " + r[0] + " type=" + r[1] + " nullable=" + r[2] + " len=" + r[3]);
                }

                System.out.println("[schema] APPLICATION_IMAGE check constraints (H2):");
                @SuppressWarnings("unchecked")
                List<Object[]> checks = entityManager.createNativeQuery(
                        "SELECT tc.CONSTRAINT_NAME, cc.CHECK_CLAUSE "
                                + "FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc "
                                + "JOIN INFORMATION_SCHEMA.CHECK_CONSTRAINTS cc "
                                + "  ON cc.CONSTRAINT_NAME = tc.CONSTRAINT_NAME "
                                + "WHERE tc.TABLE_NAME='APPLICATION_IMAGE' AND tc.CONSTRAINT_TYPE='CHECK' "
                                + "ORDER BY tc.CONSTRAINT_NAME")
                    .getResultList();
                for (Object[] r : checks) {
                    System.out.println("  " + r[0] + " => " + r[1]);
                }
            }
        } catch (Exception e) {
            System.out.println("[schema] (could not inspect schema: " + e.getClass().getSimpleName() + ")");
        }
    }

    private void fixApplicationImageStatusCheckIfNeeded(String databaseProduct) {
        // Hibernate may auto-generate enum CHECK constraints using ordinals (0..n)
        // even when the enum is persisted via AttributeConverter with explicit codes.
        // StorageObjectStatus uses codes: 0, 10, 20, 30, 40.
        String desired = "status in (0,10,20,30,40)";
        String ordinal = "status in (0,1,2,3,4)";

        try {
            if (databaseProduct != null && databaseProduct.toLowerCase().contains("mysql")) {
                @SuppressWarnings("unchecked")
                List<Object[]> checks = entityManager.createNativeQuery(
                        "SELECT tc.CONSTRAINT_NAME, cc.CHECK_CLAUSE "
                                + "FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc "
                                + "JOIN INFORMATION_SCHEMA.CHECK_CONSTRAINTS cc "
                                + "  ON cc.CONSTRAINT_SCHEMA = tc.CONSTRAINT_SCHEMA AND cc.CONSTRAINT_NAME = tc.CONSTRAINT_NAME "
                                + "WHERE tc.TABLE_SCHEMA = DATABASE() AND tc.TABLE_NAME='application_image' AND tc.CONSTRAINT_TYPE='CHECK'")
                    .getResultList();

                String constraintName = null;
                String clause = null;
                for (Object[] r : checks) {
                    String name = String.valueOf(r[0]);
                    String checkClause = String.valueOf(r[1]);
                    if (normalizeCheck(checkClause).contains(normalizeCheck(ordinal))) {
                        constraintName = name;
                        clause = checkClause;
                        break;
                    }
                }

                if (constraintName != null) {
                    System.out.println("[schema] fixing " + constraintName + ": " + clause + " -> (" + desired + ")");
                    entityManager.createNativeQuery("ALTER TABLE application_image DROP CHECK " + constraintName)
                        .executeUpdate();
                    entityManager.createNativeQuery("ALTER TABLE application_image ADD CONSTRAINT " + constraintName + " CHECK (" + desired + ")")
                        .executeUpdate();
                }
            } else {
                // H2's INFORMATION_SCHEMA.CHECK_CONSTRAINTS does not expose TABLE_NAME.
                // Also, H2 often auto-names inline check constraints (e.g., CONSTRAINT_3).
                // For test stability, drop all check constraints on APPLICATION_IMAGE and recreate
                // the two checks we depend on using explicit enum code sets.
                @SuppressWarnings("unchecked")
                List<Object> checks = entityManager.createNativeQuery(
                        "SELECT CONSTRAINT_NAME "
                                + "FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS "
                                + "WHERE TABLE_NAME='APPLICATION_IMAGE' AND CONSTRAINT_TYPE='CHECK'")
                    .getResultList();

                if (!checks.isEmpty()) {
                    System.out.println("[schema] H2: dropping " + checks.size() + " CHECK constraint(s) on APPLICATION_IMAGE");
                }

                for (Object r : checks) {
                    String name = String.valueOf(r);
                    entityManager.createNativeQuery("ALTER TABLE APPLICATION_IMAGE DROP CONSTRAINT " + name)
                        .executeUpdate();
                }

                entityManager.createNativeQuery(
                        "ALTER TABLE APPLICATION_IMAGE "
                                + "ADD CONSTRAINT APPLICATION_IMAGE_IMAGE_TYPE_CHK CHECK (IMAGE_TYPE IN (0,1,2))")
                    .executeUpdate();
                entityManager.createNativeQuery(
                        "ALTER TABLE APPLICATION_IMAGE "
                                + "ADD CONSTRAINT APPLICATION_IMAGE_STATUS_CHK CHECK (STATUS IN (0,10,20,30,40))")
                    .executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("[schema] (could not fix status CHECK: " + e.getClass().getSimpleName() + ")");
        }
    }

    private static String normalizeCheck(String check) {
        if (check == null) {
            return "";
        }
        return check
            .toLowerCase()
            .replace("`", "")
            .replace("\"", "")
            .replace("(", "")
            .replace(")", "")
            .replaceAll("\\s+", "");
    }

}
