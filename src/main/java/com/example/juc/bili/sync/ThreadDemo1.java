package com.example.juc.bili.sync;

// synchronized 实现线程通信
class Share {
    private Integer number = 0;

    // +1
    public synchronized void incr() throws InterruptedException {
        // 判断 是否是0 不是0等待
        while (number != 0) {
            this.wait();
        }
        // 干活
        number++;
        System.out.println(Thread.currentThread().getName() + ": " + number);
        // 通知
        this.notifyAll();
    }

    // +1
    public synchronized void del() throws InterruptedException {
        // 判断
        while (number != 1) {
            this.wait();
        }
        // 干活
        number--;
        System.out.println(Thread.currentThread().getName() + ": " + number);
        // 通知
        this.notifyAll();
    }
}

public class ThreadDemo1 {
    public static void main(String[] args) {
        Share share = new Share();
        // 创建线程
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.incr(); // +1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.del(); // -1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();
    }
}
