package com.personal.store.common.domain;

public enum ApplicationImageType {
    ICON((short) 0x0000),
    SCREENSHOT((short) 0x0001),
    BANNER((short) 0x0002);

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
