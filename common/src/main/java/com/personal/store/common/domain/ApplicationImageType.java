package com.personal.store.common.domain;

public enum ApplicationImageType {
    ICON((short) 0),
    SCREENSHOT((short) 1),
    BANNER((short) 2);

    private final short code;

    ApplicationImageType(short code) {
        this.code = code;
    }

    public short getCode() {
        return code;
    }

    public static ApplicationImageType fromCode(Short code) {
        if (code == null) {
            return null;
        }
        for (ApplicationImageType type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown ApplicationImageType code: " + code);
    }
}
