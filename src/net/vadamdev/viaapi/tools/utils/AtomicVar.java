package net.vadamdev.viaapi.tools.utils;

/**
 * @author VadamDev
 * @since 05/09/2022
 */
public class AtomicVar<T> {
    private T t;

    public AtomicVar(T t) {
        this.t = t;
    }

    public void set(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }
}
