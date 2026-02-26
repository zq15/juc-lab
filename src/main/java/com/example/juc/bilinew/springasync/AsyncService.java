package com.example.juc.bilinew.springasync;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class AsyncService {

    /**
     * 无返回值的异步方法
     */
    @Async
    public void doTaskVoid(String name) throws InterruptedException {
        System.out.println("[" + name + "] 开始执行，线程: " + Thread.currentThread().getName());
        Thread.sleep(1000);
        System.out.println("[" + name + "] 执行完毕");
    }

    /**
     * 有返回值的异步方法，返回 Future
     */
    @Async
    public Future<String> doTaskWithResult(String name) throws InterruptedException {
        System.out.println("[" + name + "] 开始执行，线程: " + Thread.currentThread().getName());
        Thread.sleep(1000);
        System.out.println("[" + name + "] 执行完毕");
        return new AsyncResult<>("result of " + name);
    }

    // ========== 异常处理案例 ==========

    /**
     * 案例1：void 方法抛异常
     * 异常会被 AsyncUncaughtExceptionHandler 捕获，调用方感知不到
     */
    @Async
    public void throwVoid(String name) {
        System.out.println("[" + name + "] 执行中，即将抛出异常，线程: " + Thread.currentThread().getName());
        throw new RuntimeException("void 方法的异常: " + name);
    }

    /**
     * 案例2：Future 方法抛异常
     * 异常被包装在 Future 里，调用方通过 future.get() 捕获 ExecutionException
     */
    @Async
    public Future<String> throwWithFuture(String name) {
        System.out.println("[" + name + "] 执行中，即将抛出异常，线程: " + Thread.currentThread().getName());
        throw new RuntimeException("Future 方法的异常: " + name);
    }

    /**
     * 案例3：CompletableFuture 方法抛异常
     * 异常被包装，调用方可用 .exceptionally() 或 .handle() 处理
     */
    @Async
    public CompletableFuture<String> throwWithCompletableFuture(String name) {
        System.out.println("[" + name + "] 执行中，即将抛出异常，线程: " + Thread.currentThread().getName());
        throw new RuntimeException("CompletableFuture 方法的异常: " + name);
    }
}
