package net.vadamdev.viapi.tools.tuple;

import java.util.Map;

/**
 * @author VadamDev
 * @since 02/09/2023
 */
public class ImmutablePair<L, R> implements Pair<L, R> {
    private final L left;
    private final R right;

    public ImmutablePair(final L left, final R right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public L getLeft() {
        return left;
    }

    @Override
    public R getRight() {
        return right;
    }

    public static <L, R> ImmutablePair<L, R> of(final L left, final R right) {
        return new ImmutablePair<>(left, right);
    }

    public static <L, R> ImmutablePair<L, R> of(final Map.Entry<L, R> entry) {
        return new ImmutablePair<>(entry.getKey(), entry.getValue());
    }
}
