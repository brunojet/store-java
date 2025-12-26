package com.personal.store.common.domain.abstraction;

public enum StorageObjectStatus {
    PENDING((short) 0x0000),
    PROCESSING((short) 0x000A),
    AVAILABLE((short) 0x0014),
    FAILED((short) 0x001E),
    DELETED((short) 0x0028);
    private final short code;

    StorageObjectStatus(short code) {
        this.code = code;
    }

    public short getCode() {
        return code;
    }

    public static StorageObjectStatus fromCode(Short code) {
        if (code == null) {
            return null;
        }
        for (StorageObjectStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown StorageObjectStatus code: " + code);
    }
}
