package com.example.juc.bilinew.locks;

import java.util.concurrent.locks.ReentrantLock;

// 公平锁与非公平锁的案例
class Ticket { // 资源类，模拟3个售票员卖50张票
    private int number = 50;
    ReentrantLock lock = new ReentrantLock(true); // true为公平锁，false为非公平锁，默认是非公平锁

    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() +  " 卖出第：" + number-- + " 还剩下：" + number);
            }
        } finally {
            lock.unlock();
        }
    }
}

public class SaleTicketDemo {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(() -> {
            for (int i = 0; i < 55; i++) {
                ticket.sale();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 55; i++) {
                ticket.sale();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 55; i++) {
                ticket.sale();
            }
        }, "C").start();
    }
}
