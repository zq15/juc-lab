package com.example.juc.bili.rwlock;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

class MyCache_stamped {

    // 创建 map 集合
    private volatile Map<String, Object> map = new HashMap<>();

    // 创建stamped锁对象
    private StampedLock stampedLock = new StampedLock();

    // 放数据
    public void put(String key, Object value) {
        // 添加写锁
        long writeLock = stampedLock.writeLock();

        try {
            // 放数据
            map.put(key, value);
        } finally {
            // 释放写锁
            stampedLock.unlockWrite(writeLock);
        }
    }

    // 取数据
    public Object get(String key) {
        // 添加乐观读锁
        long tryOptimisticRead = stampedLock.tryOptimisticRead();

        Object result = map.get(key);

        if (!stampedLock.validate(tryOptimisticRead)) { // 检查在读取数据后是否有写操作发生
            // 添加悲观读锁
            tryOptimisticRead = stampedLock.readLock();

            try {
                result = map.get(key);
            } finally {
                stampedLock.unlockRead(tryOptimisticRead);
            }
        }

        // 取数据
        return result;
    }

}

public class StampedLockDemo {

    public static void main(String[] args) throws InterruptedException {
        MyCache_stamped myCache = new MyCache_stamped();

        // 创建线程写数据
        for (int i = 0; i <= 5; i++) {
            final int num = i;
            new Thread(() -> {
                myCache.put(num + "", num + "");
            }, String.valueOf(i)).start();
        }

        TimeUnit.MICROSECONDS.sleep(300);

        // 创建线程读数据
        for (int i = 0; i <= 5; i++) {
            final int num = i;
            new Thread(() -> {
                System.out.println(myCache.get(num + ""));
            }, String.valueOf(i)).start();
        }
    }
}
