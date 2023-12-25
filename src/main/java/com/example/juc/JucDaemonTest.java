package com.example.juc;

public class JucDaemonTest {

    public static void main(String[] args) {
        Thread aa = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "::" + Thread.currentThread().isDaemon());
            while (true) {
            }
        }, "aa");

        // 设置守护进程
//        aa.setDaemon(true);
        aa.start();
        System.out.println(Thread.currentThread().getName() + " over");
    }

}
