package com.example.juc.bili.rwlock;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 在 MyCache_lazy 的基础上，添加了延迟加载
 */
class MyCache_lazy {

    // 创建 map 集合
    private volatile Map<String, Object> map = new HashMap<>();

    // 创建读写锁对象
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    // 放数据
    public void put(String key, Object value) {
        // 添加写锁
        rwLock.writeLock().lock();

        try {
            System.out.println(Thread.currentThread().getName() + " 正在写操作 " + key);

            // 暂停一会儿
            TimeUnit.MICROSECONDS.sleep(300);
            // 放数据
            map.put(key, value);

            System.out.println(Thread.currentThread().getName() + " 已经写完了 " + key);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放写锁
            rwLock.writeLock().unlock();
        }

    }

    // 取数据
    public Object get(String key) {
        // 添加读锁
        rwLock.readLock().lock();

        Object result = null;

        try {
            System.out.println(Thread.currentThread().getName() + " 正在读取操作 " + key);
            // 暂停一会儿
            TimeUnit.MICROSECONDS.sleep(300);
            result = map.get(key);

            System.out.println(Thread.currentThread().getName() + " 取完了 " + key);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            rwLock.readLock().unlock();
        }

        // 判断是否为空
        if (result != null) {
            return result;
        }

        // 如果为空，就加写锁
        rwLock.writeLock().lock();

        try {
            // 再次判断是否为空
            result = map.get(key);
            // 重点注意：这里必须要再次判断，因为可能有多个线程同时进入到这里，避免重复放数据
            if (result == null) {
                // 为空，就放数据(这里模拟从数据库中取出)
                result = "zhouzhou666";
                map.put(key, result);
            }
        } finally {
            // 释放写锁
            rwLock.writeLock().unlock();
        }

        // 取数据
        return result;
    }

}

public class ReadWriteLockDemo_lazy {

    public static void main(String[] args) throws InterruptedException {
        MyCache_lazy myCache = new MyCache_lazy();

        // 创建线程写数据
        for (int i = 0; i <= 5; i++) {
            final int num = i;
            new Thread(() -> {
                myCache.put(num + "", num + "");
            }, String.valueOf(i)).start();
        }

        TimeUnit.MICROSECONDS.sleep(300);

        // 创建线程读数据
        for (int i = 0; i <= 10; i++) {
            final int num = i;
            new Thread(() -> {
                System.out.println("取出的值是: "+ myCache.get(num + ""));
            }, String.valueOf(i)).start();
        }
    }
}
