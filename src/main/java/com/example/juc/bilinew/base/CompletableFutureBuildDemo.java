package com.example.juc.bilinew.base;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureBuildDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        // CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
        //     System.out.println(Thread.currentThread().getName()); // ForkJoinPool.commonPool-worker-25
        //     // 暂停几秒钟线程
        //     try {
        //         Thread.sleep(3000);
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // });
        // System.out.println(completableFuture.get()); // null

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName()); // ForkJoinPool.commonPool-worker-25
            // 暂停几秒钟线程
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello supplyAsync";
        }, threadPool);
        System.out.println(completableFuture.get()); // hello supplyAsync

        threadPool.shutdown();
    }
}
