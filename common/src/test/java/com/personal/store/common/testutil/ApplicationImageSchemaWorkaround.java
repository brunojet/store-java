package com.personal.store.common.testutil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Session;

import jakarta.persistence.EntityManager;

public final class ApplicationImageSchemaWorkaround {

    private static final Pattern IN_LIST_PATTERN = Pattern.compile(
        "(?i)(?:\\\"?)(IMAGE_TYPE|STATUS)(?:\\\"?)\\s+IN\\s*\\(([^)]*)\\)");

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
            if (hasExpectedChecks(entityManager)) {
                return;
            }

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

    private static boolean hasExpectedChecks(EntityManager entityManager) {
        @SuppressWarnings("unchecked")
        List<Object> clauses = entityManager.createNativeQuery(
                "SELECT cc.CHECK_CLAUSE "
                        + "FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc "
                        + "JOIN INFORMATION_SCHEMA.CHECK_CONSTRAINTS cc "
                        + "  ON cc.CONSTRAINT_NAME = tc.CONSTRAINT_NAME "
                        + "WHERE tc.TABLE_NAME='APPLICATION_IMAGE' AND tc.CONSTRAINT_TYPE='CHECK'")
            .getResultList();

        boolean imageTypeOk = false;
        boolean statusOk = false;
        for (Object c : clauses) {
            String clause = c == null ? "" : String.valueOf(c);
            Matcher matcher = IN_LIST_PATTERN.matcher(clause);
            while (matcher.find()) {
                String column = matcher.group(1).toUpperCase();
                Set<Integer> values = parseIntSet(matcher.group(2));
                if ("IMAGE_TYPE".equals(column)) {
                    imageTypeOk = values.equals(Set.of(0, 1, 2));
                } else if ("STATUS".equals(column)) {
                    statusOk = values.equals(Set.of(0, 10, 20, 30, 40));
                }
            }
        }

        return imageTypeOk && statusOk;
    }

    private static Set<Integer> parseIntSet(String csv) {
        Set<Integer> values = new HashSet<>();
        if (csv == null || csv.isBlank()) {
            return values;
        }
        for (String part : csv.split(",")) {
            String trimmed = part.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            try {
                values.add(Integer.parseInt(trimmed));
            } catch (NumberFormatException ignored) {
                // If parsing fails, keep the set incomplete; caller will treat as mismatch.
            }
        }
        return values;
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
