package com.example.juc.bilinew.interrupt.LockSupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {
    public static void main(String[] args) {
        /**
         * t1	 -----------come in
         * t2	 ----------发出通知
         * t1	 ----------被唤醒
         */
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t -----------come in");
            LockSupport.park();
            System.out.println("第一次park");
            LockSupport.park();
            System.out.println("第二次park");
            System.out.println(Thread.currentThread().getName() + "\t ----------被唤醒");
        }, "t1");
        t1.start();

        // try {
        //     TimeUnit.SECONDS.sleep(1);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        new Thread(() -> {
            LockSupport.unpark(t1);
            LockSupport.unpark(t1);
            LockSupport.unpark(t1);
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName() + "\t ----------发出通知");
        }, "t2").start();

    }
}
