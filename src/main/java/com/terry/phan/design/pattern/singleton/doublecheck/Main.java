package com.terry.phan.design.pattern.singleton.doublecheck;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * Singleton using double checking to prevent race condition.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<Singleton> result1 = pool.submit(Singleton::getInstance);
        Future<Singleton> result2 = pool.submit(Singleton::getInstance);

        Thread.sleep(500l);
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
        pool.shutdownNow();

        System.out.println(String.format("First singleton: %s", result1.get()));
        System.out.println(String.format("Second singleton: %s", result2.get()));
        System.out.println();
        System.out.println(String.format("They are the same: %s", result1.get() == result2.get()));
    }
}

class Singleton {
    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (Objects.nonNull(instance)) {
            return instance;
        }
        instance = getOrElseCreate();
        return instance;
    }

    private static Singleton getOrElseCreate() {
        synchronized (Singleton.class) {
            return Optional.ofNullable(instance).orElse(new Singleton());
        }
    }
}
