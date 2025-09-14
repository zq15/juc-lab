package com.example.juc.bilinew.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// ReentrantLock可中断特性演示
public class ReentrantLockInterruptDemo {

    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        // 线程1先获取锁并持有
        Thread thread1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("Thread-1 获取锁，持有3秒...");
                Thread.sleep(3000);
                System.out.println("Thread-1 释放锁");
            } catch (InterruptedException e) {
                System.out.println("Thread-1 被中断");
            } finally {
                lock.unlock();
            }
        }, "Thread-1");

        // 线程2尝试获取锁，但可以被中断
        Thread thread2 = new Thread(() -> {
            try {
                System.out.println("Thread-2 尝试获取锁...");
                // 可中断的锁获取方式
                lock.lockInterruptibly();
                try {
                    System.out.println("Thread-2 成功获取锁");
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                System.out.println("Thread-2 被中断，放弃获取锁");
            }
        }, "Thread-2");

        // 对比：使用普通lock()方法的线程无法被中断
        Thread thread3 = new Thread(() -> {
            try {
                System.out.println("Thread-3 尝试获取锁（普通lock方法）...");
                lock.lock();
                try {
                    System.out.println("Thread-3 成功获取锁");
                } finally {
                    lock.unlock();
                }
            } catch (Exception e) {
                System.out.println("Thread-3 发生异常: " + e.getMessage());
            }
        }, "Thread-3");

        System.out.println("=== ReentrantLock可中断特性演示 ===");

        thread1.start();
        Thread.sleep(500); // 让thread1先获取锁
        thread2.start();
        thread3.start();

        Thread.sleep(1000); // 让thread2和thread3等待一会儿
        System.out.println("主线程中断Thread-2和Thread-3");
        thread2.interrupt();
        thread3.interrupt();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("演示结束");
    }
}