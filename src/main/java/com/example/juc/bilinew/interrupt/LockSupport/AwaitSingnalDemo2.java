package com.example.juc.bilinew.interrupt.LockSupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 使用Condition的await和signal方法实现线程等待和唤醒
// 多条件变量的场景
public class AwaitSingnalDemo2 {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        /**
         * t1	 -----------等待条件1
         * t2	 -----------等待条件2
         * t3	 -----------唤醒条件1
         * t1	 -----------条件1满足
         * t3	 -----------唤醒条件2
         * t2	 -----------条件2满足
         */
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t -----------等待条件1");
                condition1.await();
                System.out.println(Thread.currentThread().getName() + "\t -----------条件1满足");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t -----------等待条件2");
                condition2.await();
                System.out.println(Thread.currentThread().getName() + "\t -----------条件2满足");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t2").start();
        // 等待一段时间，确保线程t1和t2都已经开始等待
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 唤醒线程t1和t2
        new Thread(() -> {
            lock.lock();
            try {
                condition1.signal();
                System.out.println(Thread.currentThread().getName() + "\t -----------唤醒条件1");
                TimeUnit.SECONDS.sleep(1);
                condition2.signal();
                System.out.println(Thread.currentThread().getName() + "\t -----------唤醒条件2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t3").start();
    }
}
