package com.example.juc.bilinew.completablefuture.examples;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CompletableFutureCombine {

    public static void main(String[] args) {
        // thenCombine示例
        CompletableFuture<Integer> future1 = CompletableFuture
                .supplyAsync(() -> {
                    int number = new Random().nextInt(10);
                    System.out.println("第一阶段：" + number);
                    return number;
                });
        CompletableFuture<Integer> future2 = CompletableFuture
                .supplyAsync(() -> {
                    int number = new Random().nextInt(10);
                    System.out.println("第二阶段：" + number);
                    return number;
                });
        CompletableFuture<Integer> result = future1
                .thenCombine(future2, new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer x, Integer y) {
                        return x + y;
                    }
                });
        System.out.println("最终结果：" + result.join());

        // acceptEither示例
        CompletableFuture<Integer> future3 = CompletableFuture
                .supplyAsync(() -> {
                    int number = new Random().nextInt(10) + 1;
                    try {
                        TimeUnit.SECONDS.sleep(number);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("第一阶段：" + number);
                    return number;
                });

        CompletableFuture<Integer> future4 = CompletableFuture
                .supplyAsync(() -> {
                    int number = new Random().nextInt(10) + 1;
                    try {
                        TimeUnit.SECONDS.sleep(number);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("第二阶段：" + number);
                    return number;
                });

        future3.acceptEither(future4, new Consumer<Integer>() {
            @Override
            public void accept(Integer number) {
                System.out.println("最快结果：" + number);
            }
        }).join();

        // runAfterEither示例
        CompletableFuture<Integer> future5 = CompletableFuture
                .supplyAsync(() -> {
                    int number = new Random().nextInt(5);
                    try {
                        TimeUnit.SECONDS.sleep(number);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("第一阶段：" + number);
                    return number;
                });

        CompletableFuture<Integer> future6 = CompletableFuture
                .supplyAsync(() -> {
                    int number = new Random().nextInt(5);
                    try {
                        TimeUnit.SECONDS.sleep(number);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("第二阶段：" + number);
                    return number;
                });

        future5.runAfterEither(future6, new Runnable() {
            @Override
            public void run() {
                System.out.println("已经有一个任务完成了");
            }
        }).join();

        // runAfterBoth示例
        CompletableFuture<Integer> future7 = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("第一阶段：1");
                    return 1;
                });

        CompletableFuture<Integer> future8 = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("第二阶段：2");
                    return 2;
                });

        future7.runAfterBoth(future8, new Runnable() {
            @Override
            public void run() {
                System.out.println("上面两个任务都执行完成了。");
            }
        }).join();
    }
}