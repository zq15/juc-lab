package com.example.juc.bilinew.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

// 可重入锁案例
public class ReEntryLockDemo {

    // 递归方法演示可重入性
    public static void recursiveMethod(Object lock, int depth) {
        if (depth <= 0) return;

        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + " - 递归深度: " + depth);
            recursiveMethod(lock, depth - 1);
        }
    }

    // ReentrantLock递归方法
    private static Lock reentrantLock = new ReentrantLock();

    public static void recursiveReentrantLock(int depth) {
        if (depth <= 0) return;

        reentrantLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " - ReentrantLock递归深度: " + depth);
            recursiveReentrantLock(depth - 1);
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final Object lock = new Object();

        // synchronized可重入锁演示
        new Thread(() -> {
            System.out.println("=== synchronized可重入锁演示 ===");
            recursiveMethod(lock, 3);
        }, "Thread-1").start();

        // ReentrantLock可重入锁演示
        new Thread(() -> {
            System.out.println("=== ReentrantLock可重入锁演示 ===");
            recursiveReentrantLock(3);
        }, "Thread-2").start();
    }
}
