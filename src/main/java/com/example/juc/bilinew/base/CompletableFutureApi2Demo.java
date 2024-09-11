package com.example.juc.bilinew.base;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

// 对运算结果的异常处理 thenApply 和 handle
public class CompletableFutureApi2Demo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        // supplyDemo();
        handleDemo();
    }

    private static void supplyDemo() {
        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        CompletableFuture.supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(111);
                    return 1;
                }, threadPool)
                .thenApply(f -> {
                    int i = 1 / 0;
                    System.out.println(222);
                    return f + 2;
                }).thenApply(f -> {
                    System.out.println(333);
                    return f + 3;
                })
                .whenComplete((v, e) -> {
                    if (e == null) {
                        System.out.println("------计算结果: " + v);
                    }
                }).exceptionally(e -> {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                    return null;
                });
        System.out.println(Thread.currentThread().getName() + "\t" + "主线程先去忙其他业务");
        threadPool.shutdown();
    }

    private static void handleDemo() {
        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(111);
            return 1;
        }, threadPool).handle((f, v) -> {
            int i = 1 / 0;
            System.out.println(222);
            return f + 2;
        }).handle((f, v) -> {
            System.out.println(333);
            // 这里会抛出空指针异常（NullPointerException）的原因是：
            // 上一个handle方法中发生了除零异常（ArithmeticException），
            // 导致该方法返回了一个包含异常的CompletableFuture。
            // 当异常发生时，handle方法的返回值（即这里的f）会被设置为null。
            // 因此，尝试对null进行加法操作（f + 3）会导致空指针异常。
            return f + 3; // java.lang.NullPointerException
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("------计算结果: " + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        });

        System.out.println(Thread.currentThread().getName() + "\t" + "主线程先去忙其他业务");
        threadPool.shutdown();
    }
}
