package com.example.juc.callable;

// 比较两个接口
// 实现 Runnable 接口

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class MyThread1 implements Runnable {

    @Override
    public void run() {

    }
}

// 实现 Callable 接口
class MyThread2 implements Callable {

    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " come in callable");
        return 200;
    }
}

public class Demo1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Runnable 接口创建线程
//        new Thread(new MyThread1(), "AA").start();

        // Callable 接口创建线程
        // 不能直接替换创建
//        new Thread(new MyThread2(), "BB").start();

        // FutureTask
        FutureTask<Integer> futureTask1 = new FutureTask<Integer>(new MyThread2());

        // lambda表达式
        FutureTask<Integer> futureTask2 = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + " come in callable");
            return 1024;
        });

        // 创建一个线程
        new Thread(futureTask2, "lucy").start();
        new Thread(futureTask1, "marry").start();


        while (!futureTask2.isDone()) {
            System.out.println("wait...");
        }

        // 调用 FutureTask get 方法
        System.out.println(futureTask2.get());

        // 第二次不会计算
//        System.out.println(futureTask2.get());

        System.out.println(futureTask1.get());

        System.out.println(Thread.currentThread().getName() + " over");
    }
}
