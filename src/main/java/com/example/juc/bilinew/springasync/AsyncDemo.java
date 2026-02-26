package com.example.juc.bilinew.springasync;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.Future;

/**
 * 测试 Spring @Async 默认行为（不配置自定义 Executor）
 *
 * 结论：
 *   Spring 默认使用 SimpleAsyncTaskExecutor
 *   - 每次 @Async 调用都新建一个线程（非线程池，不复用）
 *   - 线程名格式为 "SimpleAsyncTaskExecutor-N"
 *   - 适合低并发场景；高并发下应配置 ThreadPoolTaskExecutor
 */
public class AsyncDemo {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        AsyncService service = context.getBean(AsyncService.class);

        System.out.println("主线程: " + Thread.currentThread().getName());
        System.out.println("---------------------------------------------");

        // 连续提交 5 个任务，观察线程名是否每次都是新线程
        System.out.println("提交 5 个 void 异步任务：");
        for (int i = 1; i <= 5; i++) {
            service.doTaskVoid("task-" + i);
        }
        System.out.println("主线程：5 个任务已提交，主线程不阻塞继续运行");

        Thread.sleep(200);
        System.out.println("---------------------------------------------");

        // 带返回值：两个任务并发跑，total 耗时约 1s（不是 2s）
        System.out.println("提交 2 个带返回值的异步任务：");
        long start = System.currentTimeMillis();
        Future<String> f1 = service.doTaskWithResult("result-1");
        Future<String> f2 = service.doTaskWithResult("result-2");
        System.out.println(f1.get());
        System.out.println(f2.get());
        System.out.println("两个任务总耗时: " + (System.currentTimeMillis() - start) + " ms（并发，约 1000ms）");

        context.close();
    }
}
