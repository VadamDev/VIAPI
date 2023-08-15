package net.vadamdev.viapi;

/**
 * @author VadamDev
 * @since 13/09/2021
 *
 * Represents all public version of the VIAPI
 * There are some versions not written here because they are not public version.
 */
public enum APIVersion {
    /*
       2.x.x
     */

    V2_2_0,
    V2_2_1,
    V2_3_0,
    V2_3_5,
    V2_3_6,
    V2_3_7,
    V2_3_8,
    V2_3_9,
    V2_4_0,
    V2_4_1,
    V2_4_2,
    V2_4_3,
    V2_4_4,
    V2_4_5,
    V2_4_6,
    V2_4_7,
    V2_4_8,
    V2_4_9,
    V2_4_10,
    V2_4_11,
    V2_4_12,
    V2_4_13,
    V2_4_14,
    V2_4_15,
    V2_4_16,
    V2_4_17,

    /*
       3.x.x
     */

    V3_0_0(true), //Complete Rewrite

    UNKNOWN;

    private final boolean latest;

    APIVersion() {
        this(false);
    }

    APIVersion(boolean latest) {
        this.latest = latest;
    }

    public boolean isLatest() {
        return latest;
    }
}
