package net.vadamdev.viapi.tools.utils;

/**
 * @author VadamDev
 * @since 02/09/2023
 */
@FunctionalInterface
public interface TriConsumer<T, U, V> {
    void accept(T t, U u, V v);
}
