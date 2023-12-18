package com.github.zeekoe.bluebird;

import java.time.LocalDateTime;

public class Retryer<T extends Runnable> {
    private static final int MAX_RETRY_COUNT = 5;
    private final T runnable;
    private int retryCount;

    public Retryer(Class<T> klazz) {
        try {
            this.runnable = klazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void startRunning() {
        while (retryCount <= MAX_RETRY_COUNT) {
            try {
                runnable.run();
                retryCount = 1;
            } catch (Exception e) {
                retryCount++;
                System.out.println(LocalDateTime.now() + ": Exception, retry count: " + retryCount);
                System.out.println(e.getMessage());
            }
            if (retryCount <= MAX_RETRY_COUNT) {
                sleep(30_000L * retryCount * retryCount);
            }
        }
        System.out.println("Giving up.");
    }

    void sleep(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


