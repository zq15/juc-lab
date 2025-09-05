package com.example.juc.bili.rwlock;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.TimeUnit;

// 演示 锁升级
class SharedResource {
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private int data = 0;

    public void read() {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 获得读锁，开始读取数据");
            TimeUnit.SECONDS.sleep(3); // 模拟读操作耗时
            System.out.println(Thread.currentThread().getName() + " 读取的数据为: " + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " 释放读锁");
            rwLock.readLock().unlock();
        }
    }

    public void write(int newData) {
        System.out.println(Thread.currentThread().getName() + " 尝试获取写锁");
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 获得写锁，开始写入数据");
            TimeUnit.SECONDS.sleep(1); // 模拟写操作耗时
            this.data = newData;
            System.out.println(Thread.currentThread().getName() + " 写入数据: " + newData);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " 释放写锁");
            rwLock.writeLock().unlock();
        }
    }
}

public class ReadWriteDemo4 {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        // 创建一个读线程，不释放读锁
        new Thread(() -> {
            resource.read();
            try {
                // 读线程在读取后不释放锁，而是休眠一段时间
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "ReadThread").start();

        // 稍微延迟，确保读线程先获取读锁
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 创建一个写线程，尝试获取写锁
        new Thread(() -> {
            resource.write(42);
        }, "WriteThread").start();
    }

}
