package net.vadamdev.viapi.tools.enums;

/**
 * @author VadamDev
 * @since 24/08/2023
 */
public enum LockType {
    REMOVE(0),
    REPLACE(8),
    PLACE(16),
    ALL;

    private final int value;

    LockType() {
        this(-1);
    }

    LockType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
