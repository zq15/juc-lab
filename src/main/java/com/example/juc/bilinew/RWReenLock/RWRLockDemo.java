package com.example.juc.bilinew.RWReenLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyResource {
    private Map<String, String> map = new HashMap<>();
    // ====ReentrantLock 等价于 ====synchronized，之前讲解过
    Lock lock = new ReentrantLock();
    // ====ReentrantReadWriteLock 一体两面，读写互斥，读读共享
    ReadWriteLock rwlock = new ReentrantReadWriteLock();

    public void write(String key, String value) {
        rwlock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + "正在写入");
            map.put(key, value);
            // 暂停毫秒
            try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "\t" + "完成写入");
        } finally {
            rwlock.writeLock().unlock();
        }
    }

    public void read(String key)
    {
        rwlock.readLock().lock();
        try
        {
            System.out.println(Thread.currentThread().getName()+"\t"+"正在读取");
            String result = map.get(key);
            //暂停毫秒
            try { TimeUnit.MILLISECONDS.sleep( 2000); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName()+"\t"+"完成读取"+ "\t"+result);
        }finally {
            rwlock.readLock().unlock();
        }
    }
}


public class RWRLockDemo {
    public static void main(String[] args) throws InterruptedException {
        MyResource myResource = new MyResource();

        // 写锁获取后阻塞其他读写线程
        for (int i = 1; i <=10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.write(finalI + "", finalI + "");
            }, String.valueOf(i)).start();
        }

        // 读锁之间没有影响
        for (int i = 1; i <=10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.read(finalI + "");
            }, String.valueOf(i)).start();
        }

        Thread.sleep(1000);

        // 读锁会阻塞写锁的获取
        for (int i = 1; i <=10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.write(finalI + "", finalI + "");
            }, "新写锁线程" + String.valueOf(i)).start();
        }
    }
}
