package com.example.juc.bilinew.interrupt;

import java.util.concurrent.TimeUnit;

public class ThreadInterruptDemo {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + " 线程被中断，程序停止");
                    break;
                }
                System.out.println("-----------hello ThreadInterruptDemo");
            }
        }, "t1");
        thread1.start();
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //t2向t1放出协商，将t1中的中断标识位设为true，希望t1停下来
        // new Thread(() -> thread1.interrupt(), "t2").start();

        //当然，也可以t1自行设置
        thread1.interrupt();

    }
}
