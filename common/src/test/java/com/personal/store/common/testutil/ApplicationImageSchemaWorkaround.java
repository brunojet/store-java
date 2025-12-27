package com.personal.store.common.testutil;

import java.util.List;

import org.hibernate.Session;

import jakarta.persistence.EntityManager;

public final class ApplicationImageSchemaWorkaround {

    private ApplicationImageSchemaWorkaround() {
    }

    /**
     * H2 sometimes generates CHECK constraints with unexpected sets / names.
     * This helper drops all CHECK constraints on APPLICATION_IMAGE and recreates
     * the ones our tests depend on (image_type + status), but only when running on H2.
     */
    public static void fixApplicationImageChecksIfNeeded(EntityManager entityManager) {
        String databaseProduct = databaseProductName(entityManager);
        if (databaseProduct == null || !databaseProduct.toLowerCase().contains("h2")) {
            return;
        }

        try {
            @SuppressWarnings("unchecked")
            List<Object> checks = entityManager.createNativeQuery(
                    "SELECT CONSTRAINT_NAME "
                            + "FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS "
                            + "WHERE TABLE_NAME='APPLICATION_IMAGE' AND CONSTRAINT_TYPE='CHECK'")
                .getResultList();

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
        } catch (Exception ignored) {
            // Best-effort only: if schema differs, let the test fail with the real DB error.
        }
    }

    private static String databaseProductName(EntityManager entityManager) {
        try {
            Session session = entityManager.unwrap(Session.class);
            return session.doReturningWork(c -> c.getMetaData().getDatabaseProductName());
        } catch (Exception ignored) {
            return null;
        }
    }
}
