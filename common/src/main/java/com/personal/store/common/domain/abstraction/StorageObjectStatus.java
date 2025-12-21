package com.personal.store.common.domain.abstraction;

public enum StorageObjectStatus {
    PENDING((short) 0),
    PROCESSING((short) 1),
    AVAILABLE((short) 2),
    FAILED((short) 3),
    DELETED((short) 4);

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
