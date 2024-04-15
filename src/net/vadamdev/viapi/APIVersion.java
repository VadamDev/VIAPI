package net.vadamdev.viapi;

/**
 * Represents all public versions of the VIAPI
 *
 * @author VadamDev
 * @since 13/09/2021
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
    V2_4_18,

    /*
       3.x.x
     */

    V3_0_0, //Complete Rewrite
    V3_0_1, //Internal Changes
    V3_1_0(true), //INamedPacketEntity, Particle Videos, Improved EnumDirection...

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
