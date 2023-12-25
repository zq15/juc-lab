package com.example.juc.bili.sync;

// 第一步创建资源类
class Ticket {
    // 票数
    private int number = 30;

    // 操作方法卖票
    public synchronized void sale() {
        // 判断是否还有票
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + ": " + number-- + " 剩下: " + number);
        }
    }
}

public class SaleTicket {
    // 第二步，创建多个线程，调用资源类的操作方法
    public static void main(String[] args) {
        // 创建对象
        Ticket ticket = new Ticket();
        // 创建三个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 调用卖票
                for (int i = 0; i < 30; i++) {
                    ticket.sale();
                }
            }
        }, "AA").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 调用卖票
                for (int i = 0; i < 30; i++) {
                    ticket.sale();
                }
            }
        }, "BB").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 调用卖票
                for (int i = 0; i < 30; i++) {
                    ticket.sale();
                }
            }
        }, "CC").start();
    }
}
