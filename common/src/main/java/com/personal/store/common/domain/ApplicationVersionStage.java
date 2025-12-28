package com.personal.store.common.domain;

public enum ApplicationVersionStage {
    PENDING((short) 0),
    PILOT((short) 20),
    PRODUCTION((short) 30),
    ARCHIVED((short) 40);

    private final short code;

    ApplicationVersionStage(short code) {
        this.code = code;
    }

    public short getCode() {
        return code;
    }

    public static ApplicationVersionStage fromCode(Short code) {
        if (code == null) {
            return null;
        }
        for (ApplicationVersionStage stage : values()) {
            if (stage.code == code) {
                return stage;
            }
        }
        throw new IllegalArgumentException("Unknown ApplicationVersionStage code: " + code);
    }
}
