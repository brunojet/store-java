package com.personal.store.common.domain.abstraction;

public enum StorageObjectStatus {
    PENDING((short) 0),
    PROCESSING((short) 10),
    AVAILABLE((short) 20),
    FAILED((short) 30),
    DELETED((short) 40);
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
