package com.example.juc.bilinew.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// ReentrantLock与Condition接口基本使用演示 实现 await 和 notify 的功能
public class ReentrantLockConditionDemo {

    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();
    private static boolean flag = false;

    // 等待线程
    static class WaitThread implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 开始等待...");
                while (!flag) {
                    condition.await(); // 等待信号
                }
                System.out.println(Thread.currentThread().getName() + " 收到信号，继续执行");
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " 被中断");
            } finally {
                lock.unlock();
            }
        }
    }

    // 通知线程
    static class SignalThread implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 准备发送信号...");
                Thread.sleep(1000);
                flag = true;
                condition.signal(); // 发送信号
                System.out.println(Thread.currentThread().getName() + " 已发送信号");
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " 被中断");
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Condition基本使用演示 ===");

        // 基本的await/signal演示
        Thread waitThread = new Thread(new WaitThread(), "WaitThread");
        Thread signalThread = new Thread(new SignalThread(), "SignalThread");

        waitThread.start();
        Thread.sleep(100);
        signalThread.start();

        waitThread.join();
        signalThread.join();

        System.out.println("\n演示结束");
    }
}