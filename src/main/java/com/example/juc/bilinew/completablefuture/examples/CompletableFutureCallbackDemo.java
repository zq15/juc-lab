package com.example.juc.bilinew.completablefuture.examples;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class CompletableFutureCallbackDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // whenComplete和exceptionally示例
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
            if (new Random().nextInt(10) % 2 == 0) {
                int i = 12 / 0;
            }
            System.out.println("执行结束！");
            return "test";
        });

        future.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String t, Throwable action) {
                System.out.println(t + " 执行完成！");
            }
        });

        future.exceptionally(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable t) {
                System.out.println("执行失败：" + t.getMessage());
                return "异常xxxx";
            }
        }).join();

        // thenApply示例
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int result = 100;
            System.out.println("一阶段：" + result);
            return result;
        }).thenApply(number -> {
            int result = number * 3;
            System.out.println("二阶段：" + result);
            return result;
        });

        System.out.println("最终结果：" + future2.get());

        // thenCompose示例
        CompletableFuture<Integer> future3 = CompletableFuture
                .supplyAsync(() -> {
                    int number = new Random().nextInt(30);
                    System.out.println("第一阶段：" + number);
                    return number;
                })
                .thenCompose(param -> CompletableFuture.supplyAsync(() -> {
                    int number = param * 2;
                    System.out.println("第二阶段：" + number);
                    return number;
                }));
        System.out.println("最终结果: " + future3.get());
    }
}