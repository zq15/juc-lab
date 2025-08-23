package com.example.juc.bilinew.threadlocal;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class MyObject {
    public MyObject() {
        System.out.println(Thread.currentThread().getName() + "\t" + "MyObject 构造方法调用");
    }

    @Override
    protected void finalize() throws Throwable {
        // finalize 的通常目的是在对象被回收之前进行一些清理工作
        System.out.println(Thread.currentThread().getName() + "\t" + "MyObject 被回收");
    }
}

public class ReferenceDemo {
    public static void main(String[] args) {
//         strongReference();
//         softReference();
//         weakReference();
        phantomReference();
    }

    private static void softReference() {
        // 设置JVM参数：-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
        System.out.println("JVM堆内存限制为10MB，测试软引用回收");
        
        SoftReference<MyObject> softReference = new SoftReference<>(new MyObject());
        System.out.println("软引用创建完成: " + softReference.get());

        System.gc();

        // 暂停一会儿线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("第一次GC后，内存空间充足");
        System.out.println("软引用对象仍然存在: " + softReference.get());

        try {
            System.out.println("尝试分配10MB内存...");
            byte[] bytes = new byte[10 * 1024 * 1024]; // 8MB对象
            System.out.println("内存分配成功");
        } catch (OutOfMemoryError e) {
            System.out.println("内存不足异常: " + e.getMessage());
        } finally {
            System.out.println("内存压力后，检查软引用状态: " + softReference.get());
        }
    }

    private static void weakReference() {
        WeakReference<MyObject> weakReference = new WeakReference<>(new MyObject());
        System.out.println(weakReference.get());
        System.gc();
        // 暂停一会儿线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("gc 内存空间充足");
        System.out.println(weakReference.get());
    }

    /**
     * 虚引用
     */
    private static void phantomReference() {
        // 设置JVM参数：-Xms10m -Xmx10m
        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>(); // Define the ReferenceQueue
        PhantomReference<MyObject> phantomReference = new PhantomReference<>(new MyObject(), referenceQueue);
        System.out.println("gc 内存空间充足");
        // System.out.println(phantomReference.get()); // 永远为 null

        List<byte[]> list = new ArrayList<>();
        // 创建一个线程，每隔 0.1s 向 list 中添加一个 1MB 的对象
        new Thread(() -> {
            while (true) {
                list.add(new byte[1024 * 1024 * 1]);
                try {
                    TimeUnit.MICROSECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(phantomReference.get() + "添加一个 1MB 的对象");
            }
        }).start();

        // 创建一个线程，每隔 0.5s 检查一次 referenceQueue 中是否有被回收的对象
        new Thread(() -> {
            while (true) {
                Reference<? extends MyObject> reference = referenceQueue.poll(); // Use the defined referenceQueue
                if (reference != null) {
                    System.out.println("虚引用被回收：" + reference);
                    break;
                }
            }
        }).start();
    }

    private static void strongReference() {
        MyObject myObject = new MyObject();
        System.out.println("gc1 之前");
        System.gc();
        // 暂停一会儿线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("gc1 之后");

        myObject = null; // 置空后才可回收

        System.out.println("gc2 之前");
        System.gc();
        // 暂停一会儿线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("gc2 之后");
    }
}
