package com.example.juc.lock;

import java.util.concurrent.locks.ReentrantLock;

// 使用 Lock 实现卖票
class LTicket {
    // 票数
    private int number = 30;

    // 创建可重入锁
    private final ReentrantLock lock = new ReentrantLock(true);

    // 操作方法卖票
     void sale() {
         lock.lock();
         try {
             // 判断是否还有票
             if (number > 0) {
                 System.out.println(Thread.currentThread().getName() + ": " + number-- + " 剩下: " +number);
             }
         } finally {
             lock.unlock();
         }
    }
}

public class LSaleTicket {
    public static void main(String[] args) {
        LTicket lTicket = new LTicket();
        // 使用 lambda 简化
        new Thread(() -> {
           for (int i = 0;i<40;i++) {
               lTicket.sale();
           }
        }, "aa").start();
        new Thread(() -> {
            for (int i = 0;i<40;i++) {
                lTicket.sale();
            }
        }, "bb").start();
        new Thread(() -> {
            for (int i = 0;i<40;i++) {
                lTicket.sale();
            }
        }, "cc").start();
    }
}
