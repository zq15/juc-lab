package com.example.juc.bilinew.base;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        // 3个任务，目前只有一个线程 main 来处理，请问耗时多少？
        long start = System.currentTimeMillis();

        FutureTask<String> futureTask1 = new FutureTask<String>(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "task1 over";
        });
        threadPool.submit(futureTask1);

        FutureTask<String> futureTask2 = new FutureTask<String>(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "task2 over";
        });
        threadPool.submit(futureTask2);

        FutureTask<String> futureTask3 = new FutureTask<String>(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "task3 over";
        });
        threadPool.submit(futureTask3);

        System.out.println(futureTask1.get());
        System.out.println(futureTask2.get());

        long end = System.currentTimeMillis();
        System.out.println("-----costTime: " + (end - start) + " 毫秒");
        System.out.println(Thread.currentThread().getName() + "\t  -----end");
        threadPool.shutdown();
    }
}
