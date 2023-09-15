package net.vadamdev.viapi.tools.tuple;

import java.util.Map;

/**
 * @author VadamDev
 * @since 02/09/2023
 */
public class MutablePair<L, R> implements Pair<L, R> {
    private L left;
    private R right;

    public MutablePair(L left, R right) {
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

    public void setLeft(L left) {
        this.left = left;
    }

    public void setRight(R right) {
        this.right = right;
    }

    public static <L, R> MutablePair<L, R> of(L left, R right) {
        return new MutablePair<>(left, right);
    }

    public static <L, R> MutablePair<L, R> of(Map.Entry<L, R> entry) {
        return new MutablePair<>(entry.getKey(), entry.getValue());
    }
}
