package net.vadamdev.viapi.tools.tuple;

/**
 * @author VadamDev
 * @since 02/09/2023
 */
public class ImmutableTriple<L, M, R> implements Triple<L, M, R> {
    private final L left;
    private final M middle;
    private final R right;

    public ImmutableTriple(L left, M middle, R right) {
        this.left = left;
        this.middle = middle;
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

    @Override
    public M getMiddle() {
        return middle;
    }

    public static <L, M, R> ImmutableTriple<L, M, R> of(final L left, final M middle, final R right) {
        return new ImmutableTriple<>(left, middle, right);
    }
}
