package com.example.juc.book;

import java.util.concurrent.*;

public class AsyncFutureDemo {

    // 自定义线程池
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor
            (AVAILABLE_PROCESSORS, AVAILABLE_PROCESSORS * 2, 1,
                    TimeUnit.MINUTES, new LinkedBlockingDeque<>(5), new ThreadPoolExecutor.CallerRunsPolicy());

    public static String doSomethingA() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("do something");
        return "TaskAResult";
    }

    public static String doSomethingB() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("do something");
        return "TaskBResult";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        // 1. 创建 future 任务
        Future<String> futureTask = POOL_EXECUTOR.submit(() -> {
            String result = null;
            try {
                result = doSomethingA();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        });

        // 2.开启异步单元执行任务 A
//        Thread thread = new Thread(futureTask, "threadA");
//        thread.start();

        // 使用线程池创建
//        POOL_EXECUTOR.execute(futureTask);

        // 3.执行任务 B
        String taskBResult = doSomethingB();

        // 4.同步等待线程A运行结束
        String taskAResult = futureTask.get();

        // 5.打印两个任务执行结果
        System.out.println(taskAResult + " " + taskBResult);
        System.out.println(System.currentTimeMillis() - start);

    }
}
