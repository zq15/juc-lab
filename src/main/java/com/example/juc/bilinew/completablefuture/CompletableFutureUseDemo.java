package com.example.juc.bilinew.base;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class CompletableFutureUseDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        
        try {
            CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName() + "----come in"); // main
                int result = ThreadLocalRandom.current().nextInt(10);
                System.out.println("num = " + result);
                int i = 1/0;
                // 暂停几秒钟线程
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("-----一秒钟后出结果: " + result);
                return result;
            }, threadPool).whenComplete(
                // 无论如何都会进入
                    (result, throwable) -> {
                        System.out.println("进入 whenComplete");
                        if (throwable == null) {
                            System.out.println("-----计算完成 result = " + result);
                        }
                    }
            ).exceptionally(
                    throwable -> {
                        throwable.printStackTrace();
                        System.out.println("异常:" + throwable.getCause() + "\t" + throwable.getMessage());
                        return null;
                    });
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            threadPool.shutdown();
        }

        System.out.println("main 先去忙其他的");
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭，暂停三秒钟线程
        // ForkJoinPool.commonPool 中的线程都是守护线程看，main 线程是非守护线程，main 线程结束以后，非守护线程会结束
        // Thread.sleep(3);
    }

    private static void future1() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
                       System.out.println(Thread.currentThread().getName() + "----come in"); // main
                       int result = ThreadLocalRandom.current().nextInt(10);
                       System.out.println("num = " + result);
                       // 暂停几秒钟线程
                       try {
                           Thread.sleep(1000);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                       System.out.println("-----一秒钟后出结果: " + result);
                       return result;
                   });
            System.out.println(Thread.currentThread().getName() + "线程先去忙其他任务");
        
            System.out.println(completableFuture.get());
    }
}
