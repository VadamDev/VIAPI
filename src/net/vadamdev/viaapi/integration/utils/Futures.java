package net.vadamdev.viaapi.integration.utils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Futures {
    public static <T> Collector<CompletableFuture<T>, ?, CompletableFuture<List<T>>> collector() {
        return Collectors.collectingAndThen(Collectors.toList(), Futures::of);
    }

    public static <T> CompletableFuture<List<T>> of(final Collection<CompletableFuture<T>> futures) {
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApplyAsync($ -> awaitCompletion(futures));
    }

    private static <T> List<T> awaitCompletion(final Collection<CompletableFuture<T>> futures) {
        return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }
}
