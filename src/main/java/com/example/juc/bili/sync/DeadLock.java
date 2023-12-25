package com.example.juc.bili.sync;

import java.util.concurrent.TimeUnit;

public class DeadLock {
    // 创建两个对象
    static Object a = new Object();
    static Object b = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (a) {
                System.out.println(Thread.currentThread().getName() + " 持有锁a, 试图获取锁 b");
                // 添加 sleep 保证线程创建的确定性
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                synchronized (b) {
                    System.out.println(Thread.currentThread().getName() + " 获取锁 b");
                }
            }
        }, "A").start();


        new Thread(() -> {
            synchronized (b) {
                System.out.println(Thread.currentThread().getName() + " 持有锁b, 试图获取锁 b");

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                synchronized (a) {
                    System.out.println(Thread.currentThread().getName() + " 获取锁 a");
                }
            }
        }, "B").start();
    }
}
