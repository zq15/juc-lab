package com.example.juc.bili.pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// 演示线程池 三种 常用分类
public class ThreadPoolDemo1_1 {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        // 使用 execute 提交任务
        threadPool.execute(() -> {
            System.out.println(Thread.currentThread().getName() + " 使用 execute 办理业务");
        });

        // 使用 submit 提交任务
        Future<String> future = threadPool.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " 使用 submit 办理业务");
            return "任务完成";
        });

        try {
            // 获取 submit 任务的结果
            String result = future.get();
            System.out.println("submit 任务结果: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
