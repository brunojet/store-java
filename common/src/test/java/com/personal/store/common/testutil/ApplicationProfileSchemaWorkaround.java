package com.personal.store.common.testutil;

import java.util.List;

import org.hibernate.Session;

import jakarta.persistence.EntityManager;

public final class ApplicationProfileSchemaWorkaround {

    private ApplicationProfileSchemaWorkaround() {
    }

    /**
     * H2 occasionally generates/maintains CHECK constraints in a way that breaks inserts
     * (seen as "Check constraint invalid" with an empty clause name).
     * This helper ensures the STAGE constraint exists in the expected shape for H2.
     */
    public static void fixApplicationProfileStageCheckIfNeeded(EntityManager entityManager) {
        String databaseProduct = databaseProductName(entityManager);
        if (databaseProduct == null || !databaseProduct.toLowerCase().contains("h2")) {
            return;
        }

        try {
            if (hasExpectedStageCheck(entityManager)) {
                return;
            }

            @SuppressWarnings("unchecked")
            List<Object> checks = entityManager.createNativeQuery(
                    "SELECT CONSTRAINT_NAME "
                            + "FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS "
                            + "WHERE TABLE_NAME='APPLICATION_PROFILE_HISTORY' AND CONSTRAINT_TYPE='CHECK'")
                .getResultList();

            for (Object r : checks) {
                String name = String.valueOf(r);
                entityManager.createNativeQuery(
                        "ALTER TABLE APPLICATION_PROFILE_HISTORY DROP CONSTRAINT " + name)
                    .executeUpdate();
            }

            entityManager.createNativeQuery(
                    "ALTER TABLE APPLICATION_PROFILE_HISTORY "
                            + "ADD CONSTRAINT APPLICATION_PROFILE_STAGE_CHK CHECK (STAGE IN (0,1,2,3,4))")
                .executeUpdate();
        } catch (Exception ignored) {
            // Best-effort only: if schema differs, let the test fail with the real DB error.
        }
    }

    private static boolean hasExpectedStageCheck(EntityManager entityManager) {
        @SuppressWarnings("unchecked")
        List<Object> clauses = entityManager.createNativeQuery(
                "SELECT cc.CHECK_CLAUSE "
                        + "FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc "
                        + "JOIN INFORMATION_SCHEMA.CHECK_CONSTRAINTS cc "
                        + "  ON cc.CONSTRAINT_NAME = tc.CONSTRAINT_NAME "
                        + "WHERE tc.TABLE_NAME='APPLICATION_PROFILE_HISTORY' AND tc.CONSTRAINT_TYPE='CHECK'")
            .getResultList();

        for (Object c : clauses) {
            String clause = c == null ? "" : String.valueOf(c).toUpperCase();
            // Accept a few variations H2 emits
            if (clause.contains("STAGE") && clause.contains("IN")
                    && clause.contains("0") && clause.contains("1") && clause.contains("2")
                    && clause.contains("3") && clause.contains("4")) {
                return true;
            }
        }
        return false;
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
