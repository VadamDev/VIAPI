package net.vadamdev.viaapi.integration.utils;

import java.util.Arrays;
import java.util.Objects;

public final class MethodSignature {
    private final String name;
    private final Class<?>[] params;

    public MethodSignature(String name, Class<?>[] params) {
        this.name = name;
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public Class<?>[] getParams() {
        return params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodSignature that = (MethodSignature) o;
        return Objects.equals(name, that.name) && Arrays.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(params);
        return result;
    }
}
