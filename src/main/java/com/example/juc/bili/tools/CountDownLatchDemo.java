package com.example.juc.bili.tools;

import java.util.concurrent.CountDownLatch;

// 演示 countDownLatch
public class CountDownLatchDemo {
    // 六个同学陆续离开教室后，班长锁门
    public static void main(String[] args) throws InterruptedException {
        // 创建 CountDownLatch对象，设置初始值
        CountDownLatch countDownLatch = new CountDownLatch(6);


        // 六个同学陆续离开教室后
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 号同学离开了教室");

                // 计数 -1
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        countDownLatch.await();

        System.out.println(Thread.currentThread().getName() + " 班长锁门走人了 ");
    }
}
