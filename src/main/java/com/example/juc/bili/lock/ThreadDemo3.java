package com.example.juc.bili.lock;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareResource {
    // 定义标志位
    private int flag = 1;

    // 创建 Lock 锁
    private Lock lock = new ReentrantLock();

    // 创建三个 condition
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    // 打印 5 次，参数第几轮
    public void print5(int loop) throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 判断
            while (flag != 1) {
                c1.await();
            }
            // 干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + i + "轮数: " + loop);
            }
            // 通知
            flag = 2; // 修改标志位为 2
            c2.signal(); // 通知 BB 线程
        } finally {
            lock.unlock();
        }
    }

    // 打印 10 次，参数第几轮
    public void print10(int loop) throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 判断
            while (flag != 2) {
                c2.await();
            }
            // 干活
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + i + "轮数: " + loop);
            }
            // 通知
            flag = 3; // 修改标志位为 3
            c3.signal(); // 通知 BB 线程
        } finally {
            lock.unlock();
        }
    }

    // 打印 15 次，参数第几轮
    public void print15(int loop) throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 判断
            while (flag != 3) {
                c3.await();
            }
            // 干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + i + "轮数: " + loop);
            }
            // 通知
            flag = 1; // 修改标志位为 2
            c1.signal(); // 通知 BB 线程
        } finally {
            lock.unlock();
        }
    }


}

public class ThreadDemo3 {
    public static void main(String[] args) {
        ShareResource share = new ShareResource();
        // 创建线程
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    share.print5(i); // +1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();
        // 创建线程
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.print10(i); // +1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();
        // 创建线程
        new Thread(() -> {
            for (int i = 1; i <= 15; i++) {
                try {
                    share.print15(i); // +1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "CC").start();
    }
}
