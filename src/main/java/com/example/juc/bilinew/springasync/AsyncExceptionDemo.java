package com.example.juc.bilinew.springasync;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 演示 @Async 三种返回类型下的异常处理差异
 *
 * 结论：
 *   1. void    → 异常不会传播到调用方。
 *               异步方法运行在另一个线程，调用方早已返回，不存在共同的调用栈，
 *               因此 try-catch 包住调用语句没有任何效果。
 *               异常只能通过 AsyncUncaughtExceptionHandler 感知（默认只打日志）。
 *   2. Future  → 异常包装在 Future 中，future.get() 抛出 ExecutionException
 *   3. CompletableFuture → 异常包装在 CF 中，用 .exceptionally()/.handle() 处理
 */
public class AsyncExceptionDemo {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        AsyncService service = context.getBean(AsyncService.class);

        // ---- 案例 1：void 方法抛异常 ----
        System.out.println("===== 案例1：void 方法抛异常 =====");
        System.out.println("调用方：用 try-catch 包住调用，尝试捕获异常");
        try {
            service.throwVoid("void-task");
            // 异步方法提交后立即返回，异常在另一个线程抛出，这里不会被触发
            System.out.println("调用方：try 块正常执行完毕，没有捕获到任何异常");
        } catch (Exception e) {
            // 这行永远不会执行
            System.out.println("调用方：catch 块被触发（不会出现这行）");
        }
        Thread.sleep(500); // 等异步线程中的异常日志打印出来
        System.out.println("调用方：继续执行，catch 未被触发，异常由 Spring 内部处理\n");

        // ---- 案例 2：Future 方法抛异常 ----
        System.out.println("===== 案例2：Future 方法抛异常 =====");
        Future<String> future = service.throwWithFuture("future-task");
        try {
            future.get(); // 这里会抛出 ExecutionException
        } catch (ExecutionException e) {
            System.out.println("调用方捕获到 ExecutionException");
            System.out.println("原始异常: " + e.getCause().getMessage());
        }
        System.out.println();

        // ---- 案例 3：CompletableFuture 方法抛异常 ----
        System.out.println("===== 案例3：CompletableFuture 方法抛异常 =====");
        CompletableFuture<String> cf = service.throwWithCompletableFuture("cf-task");
        String result = cf.exceptionally(ex -> {
            System.out.println("exceptionally 捕获到异常: " + ex.getMessage());
            return "降级返回值";
        }).get();
        System.out.println("最终结果: " + result);

        context.close();
    }
}
