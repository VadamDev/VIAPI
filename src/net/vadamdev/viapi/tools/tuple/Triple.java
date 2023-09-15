package net.vadamdev.viapi.tools.tuple;

/**
 * @author VadamDev
 * @since 02/09/2023
 */
public interface Triple<L, M, R> extends Pair<L, R> {
    M getMiddle();
}
