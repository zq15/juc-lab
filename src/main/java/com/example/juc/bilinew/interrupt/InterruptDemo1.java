package com.example.juc.bilinew.interrupt;

import java.util.concurrent.TimeUnit;

// 对于正常运行的线程，调用interrupt()方法，会设置中断标志，然后线程继续运行
public class InterruptDemo1 {
     public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 300; i++) {
                System.out.println("------" + i);
           }
            System.out.println("t1线程调用interrupt()1后的中断标志：" + Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();

        System.out.println("t1线程的默认中断标志：" + t1.isInterrupted());

        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.interrupt();
        System.out.println("t1线程调用interrupt()2后的中断标志：" + t1.isInterrupted());

        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("t1线程调用interrupt()3后的中断标志：" + t1.isInterrupted());
    }
}
