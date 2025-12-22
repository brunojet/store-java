package com.personal.store.common.domain;

public enum ApplicationStage {
    PENDING((short) 0),
    REVIEW((short) 1),
    PILOT((short) 2),
    PRODUCTION((short) 3),
    ARCHIVED((short) 4);

    private final short code;

    ApplicationStage(short code) {
        this.code = code;
    }

    public short getCode() {
        return code;
    }

    public static ApplicationStage fromCode(Short code) {
        if (code == null) {
            return null;
        }
        for (ApplicationStage stage : values()) {
            if (stage.code == code) {
                return stage;
            }
        }
        throw new IllegalArgumentException("Unknown ApplicationStage code: " + code);
    }
}
