package com.example.juc.bili.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SyncLockDemo {

    public synchronized void add() {
        // Exception in thread "main" java.lang.StackOverflowError
        // 可重入，所以会出现递归调用
        add();
    }

    public static void main(String[] args) {

        // Lock 演示 可重入锁
        Lock lock = new ReentrantLock();

        new Thread(() -> {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "外层");
                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + "内层");
                } finally {
                    lock.unlock();
                }
            } finally {
                // 如果不释放会卡住后面的
//                lock.unlock();
            }

        }, "AA").start();
        new Thread(() -> {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName());
            } finally {
                lock.unlock();
            }

        }, "BB").start();

//        new SyncLockDemo().add();

        // syncronized
//        Object o = new Object();
//        new Thread(() -> {
//            synchronized (o) {
//                System.out.println(Thread.currentThread().getName() + "外层");
//                synchronized (o) {
//                    System.out.println(Thread.currentThread().getName() + "中层");
//                    synchronized (o) {
//                        System.out.println(Thread.currentThread().getName() + "内层");
//                    }
//                }
//            }
//        }, "AA").start();
    }
}
