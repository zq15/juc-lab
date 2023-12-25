package com.example.juc.book;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.function.Supplier;

public class TestCompletableFutureSet {

    // 自定义线程池
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor
            (AVAILABLE_PROCESSORS, AVAILABLE_PROCESSORS * 2, 1,
                    TimeUnit.MINUTES, new LinkedBlockingDeque<>(5), new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 1.创建一个CompletableFuture对象
        CompletableFuture<String> future = new CompletableFuture<>();

        // 2.开启线程计算任务结果，并设置
        POOL_EXECUTOR.execute(()->{
            // 2.1 休眠 3 秒，模拟任务计算
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 2.2 设置计算结果到 future
            System.out.println("-------" + Thread.currentThread().getName() + " set future result-----");
            future.complete("hello, jiaduo");
        });

        // 等待计算结果
        System.out.println("-----main thread wait future result----");
        System.out.println(future.get());
        System.out.println("-----main thread got future result----");
    }

    public static void runAsAsync() throws ExecutionException, InterruptedException {
        // 1.1 创建异步任务，并返回future
        CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                // 1.1.1 休眠2s 模拟任务计算
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("over");
            }
        });

        // 1.2 同步等待异步任务执行结束
        System.out.println(future.get());
    }

    private static final ThreadPoolExecutor bizPoolExecutor = new ThreadPoolExecutor(8, 8,
            1, TimeUnit.MINUTES, new LinkedBlockingDeque<>(10));

    public static void runAsAsyncWithBizExecutor() throws ExecutionException, InterruptedException {
        // 1.1 创建异步任务，并返回future
        CompletableFuture future = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                // 1.1.1 休眠2s 模拟任务计算
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("over");
            }
        }, bizPoolExecutor);

        // 1.2 同步等待异步任务执行结束
        System.out.println(future.get());
    }

    public static void runAsAsyncReturn() throws ExecutionException, InterruptedException {
        // 1.1 创建异步任务，并返回future
        CompletableFuture future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                // 1.1.1 休眠2s 模拟任务计算
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("over");
                return "hello, jiaduo";
            }
        });

        // 1.2 同步等待异步任务执行结束
        System.out.println(future.get());
    }
}
