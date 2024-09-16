package com.example.juc.bilinew.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 实现一个自旋锁
 * 
 * 自旋锁好处：循环比较获取直到成功为止，没有类似wait的阻塞。
 * 
 * 通过CAS操作完成自旋锁，A线程先进来调用myLock方法自己持有锁5秒钟，B随后进来后发现
 * 当前有线程持有锁，所以只能通过自旋等待，直到A释放锁后B随后抢到。
 */
// 利用一个 原子引用 来实现自旋锁
// lock -> 查看是否为空，为空则将当前线程设置为锁的持有者，如果不为空则自旋等待
// unlock -> 将锁的持有者设置为null
public class SpinLockDemo {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "\t come in");
        while (!atomicReference.compareAndSet(null, thread)) {
            System.out.println(thread.getName() + "\t 自旋获取锁");
        }
    }   

    public void unlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread.getName() + "\t myUnlock");
    }
    
    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(() -> {
            spinLockDemo.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.unlock();
        }, "A").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            spinLockDemo.lock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.unlock();
        }, "B").start();
    }

}
