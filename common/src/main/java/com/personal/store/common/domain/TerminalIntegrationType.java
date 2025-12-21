package com.personal.store.common.domain;

public enum TerminalIntegrationType {
    RFAL((short) 0),
    TEF((short) 1);

    private final short code;

    TerminalIntegrationType(short code) {
        this.code = code;
    }

    public short getCode() {
        return code;
    }

    public static TerminalIntegrationType fromCode(Short code) {
        if (code == null) {
            return null;
        }
        for (TerminalIntegrationType type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown TerminalIntegrationType code: " + code);
    }
}
