package com.example.juc.bilinew.base;

import java.util.concurrent.TimeUnit;

public class DaemonDemo {
    public static void main(String[] args) {
        // 测试用户线程与守护线程
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 开始运行，" + ((Thread.currentThread().isDaemon()) ? "守护线程" : "用户线程"));
            while (true) {
            }
        }, "t1");
        t1.start();

        // 暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(Thread.currentThread().getName() + "\t ----end 主线程");
    }
}
