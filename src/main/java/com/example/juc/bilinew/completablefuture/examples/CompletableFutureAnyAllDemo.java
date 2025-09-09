package com.example.juc.bilinew.completablefuture.examples;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureAnyAllDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // anyOf示例
        Random random = new Random();
        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(random.nextInt(5));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "hello";
                });

        CompletableFuture<String> future2 = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(random.nextInt(1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "world";
                });
        CompletableFuture<Object> result = CompletableFuture.anyOf(future1, future2);
        System.out.println(result.get());

        // allOf示例
        CompletableFuture<String> future3 = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("future1完成！");
                    return "future1完成！";
                });

        CompletableFuture<String> future4 = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("future2完成！");
                    return "future2完成！";
                });

        CompletableFuture<Void> combindFuture = CompletableFuture
                .allOf(future3, future4);
        try {
            combindFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("future1: " + future3.isDone() + "，future2: " + future4.isDone());
    }
}