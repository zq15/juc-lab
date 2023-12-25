package com.example.juc.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

// 演示 读写锁的降级
public class Demo1 {

    public static void main(String[] args) {
        // 可重入读写锁对象
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();
        ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();

        // 锁降级
        // 获取写锁
        rwLock.readLock().lock();
        System.out.println("---read");

        // 获取读锁
        rwLock.writeLock().lock();
        System.out.println("zhouzhou666");

        // 释放写锁
        rwLock.writeLock().unlock();

        // 释放读锁
        rwLock.readLock().unlock();
    }
}
