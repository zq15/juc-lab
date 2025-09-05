package com.example.juc.bili;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 线程创建方式演示类
 * 演示四种创建线程的方式：
 * 1. 继承Thread类
 * 2. 实现Runnable接口
 * 3. 实现Callable接口（配合FutureTask）
 * 4. 使用Lambda表达式创建Callable任务
 */
public class ThreadCreateDemo {
    
    /**
     * 方式1：继承Thread类创建线程
     * 直接继承Thread类，重写run()方法
     * 优点：简单直接，无需额外创建Thread对象
     * 缺点：Java不支持多重继承，限制了类的扩展性
     */
    static class MyThread0 extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " come in Thread");
        }
    }
    
    /**
     * 方式2：实现Runnable接口创建线程
     * Runnable接口只有一个run()方法，没有返回值，不能抛出异常
     * 优点：可以继承其他类，灵活性好
     */
    static class MyThread1 implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " come in Runnable");
        }
    }

    /**
     * 方式3：实现Callable接口创建线程
     * Callable接口有call()方法，有返回值，可以抛出异常
     * 需要配合FutureTask使用，FutureTask实现了Runnable接口
     */
    static class MyThread2 implements Callable<Integer> {

        @Override
        public Integer call() {
            System.out.println(Thread.currentThread().getName() + " come in Callable");
            return 100;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("===== 线程创建方式演示 =====");
        
        // 方式1：继承Thread类创建线程
        System.out.println("\n1. 继承Thread类创建线程：");
        new MyThread0().start();
        
        // 方式2：使用Runnable接口创建线程
        System.out.println("\n2. 使用Runnable接口创建线程：");
        new Thread(new MyThread1(), "AA").start();
        
        // 注意：不能直接将Callable对象传递给Thread，因为Thread只接受Runnable
        // 以下代码会编译错误：
        // new Thread(new MyThread2(), "BB").start();
        
        // 方式3：使用Callable + FutureTask创建线程
        System.out.println("\n3. 使用Callable接口配合FutureTask创建线程：");
        FutureTask<Integer> futureTask1 = new FutureTask<>(new MyThread2());
        new Thread(futureTask1, "BB").start();

        // 方式4：使用Lambda表达式创建Callable任务
        System.out.println("\n4. 使用Lambda表达式创建Callable任务：");
        FutureTask<Integer> futureTask2 = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + " come in callable");
            return 1024;
        });

        new Thread(futureTask2, "Lucy").start();

        // 等待FutureTask完成并获取结果
        System.out.println("\n等待任务完成...");
        while (!futureTask2.isDone()) {
            System.out.println("wait...");
            Thread.sleep(100); // 添加短暂休眠，避免CPU空转
        }

        // 获取Callable任务的返回结果
        System.out.println("\n任务结果：" + futureTask2.get());
        System.out.println("main 程序结束了");
    }
}