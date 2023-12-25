package com.example.juc.bili.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Lock 实现线程通信
class Share {
    private Integer number = 0;

    // 创建 Lock
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    // +1
    public void incr() throws InterruptedException {
        lock.lock();
        try {
            // 判断 是否是0 不是0等待
            while (number != 0) {
                condition.await();
            }
            // 干活
            number++;
            System.out.println(Thread.currentThread().getName() + ": " + number);
            // 通知
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    // +1
    public void del() throws InterruptedException {
        lock.lock();
        try {
            // 判断
            while (number != 1) {
                condition.await();
            }
            // 干活
            number--;
            System.out.println(Thread.currentThread().getName() + ": " + number);
            // 通知
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

public class ThreadDemo2 {
    public static void main(String[] args) {
        Share share = new Share();
        // 创建线程
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.incr(); // +1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.del(); // -1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();
    }
}

