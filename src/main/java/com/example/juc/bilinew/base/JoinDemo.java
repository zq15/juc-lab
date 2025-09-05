package com.example.juc.bilinew.base;

/**
 * 现在有T1、T2、T3三个线程，你怎样保证T2在T1执行完后执行，T3在T2执行完后执行？
 */
public class JoinDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("T1 is running");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("T1 is finished");
        });

        Thread t2 = new Thread(() -> {
            try {
                t1.join();
                System.out.println("T2 is running");
                Thread.sleep(1000);
                System.out.println("T2 is finished");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                t2.join();
                System.out.println("T3 is running");
                Thread.sleep(1000);
                System.out.println("T3 is finished");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
