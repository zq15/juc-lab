package com.example.juc.bili;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

// 比较两个接口
// 实现 Runnable 接口
class MyThread1 implements Runnable {
    @Override
    public void run() {

    }
}

// 实现 callable 接口
class MyThread2 implements Callable {

    @Override
    public Object call() throws Exception {
        return null;
    }
}

public class Demo1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Runnable
        new Thread(new MyThread1(), "AA").start();
        // Runnable 报错
//        new Thread(new MyThread2(), "BB").start();
        // FutureTask
        FutureTask<Integer> futureTask1 = new FutureTask<>(new MyThread2());

        // lambda 表达式
        FutureTask<Integer> futureTask2 = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + " come in callable");
            return 1024;
        });

        new Thread(futureTask2, "Lucy").start();

        while (!futureTask2.isDone()) {
            System.out.println("wait...");
        }

        System.out.println(futureTask2.get());
        System.out.println("main 程序结束了");

    }
}


