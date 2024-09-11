package com.example.juc.bilinew.base;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// 线程池选择的案例
public class CompletableFutureWithThreadPool {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        try {
            CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
                            try {
                                TimeUnit.MICROSECONDS.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("1号任务" + Thread.currentThread().getName());
                            return "abcd";
                        }, threadPool).thenRunAsync(() -> {
                            try {
                                TimeUnit.MICROSECONDS.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("2号任务" + Thread.currentThread().getName());
                        }).thenRun(() -> {
                            try {
                                TimeUnit.MICROSECONDS.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("3号任务" + Thread.currentThread().getName());
                        }).thenRun(() -> {
                            try {
                                TimeUnit.MICROSECONDS.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("4号任务" + Thread.currentThread().getName());
                        });
            System.out.println(completableFuture.get(2L, TimeUnit.SECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
