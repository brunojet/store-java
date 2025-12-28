package com.personal.store.common.domain;

public enum ApplicationProfileStage {
    PENDING((short) 0),
    REVIEW((short) 10),
    PRODUCTION((short) 30),
    ARCHIVED((short) 40);

    private final short code;

    ApplicationProfileStage(short code) {
        this.code = code;
    }

    public short getCode() {
        return code;
    }

    public static ApplicationProfileStage fromCode(Short code) {
        if (code == null) {
            return null;
        }
        for (ApplicationProfileStage stage : values()) {
            if (stage.code == code) {
                return stage;
            }
        }
        throw new IllegalArgumentException("Unknown ApplicationProfileStage code: " + code);
    }
}
