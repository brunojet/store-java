package com.personal.store.common.domain;

public enum ApplicationCatalogStage {
    REVIEW((short) 10),
    PILOT((short) 20),
    PRODUCTION((short) 30);

    private final short code;

    ApplicationCatalogStage(short code) {
        this.code = code;
    }

    public short getCode() {
        return code;
    }

    public static ApplicationCatalogStage fromCode(Short code) {
        if (code == null) {
            return null;
        }
        for (ApplicationCatalogStage stage : values()) {
            if (stage.code == code) {
                return stage;
            }
        }
        throw new IllegalArgumentException("Unknown ApplicationCatalogStage code: " + code);
    }
}
