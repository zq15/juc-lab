package com.example.juc.bilinew.volatiler;

import java.util.concurrent.TimeUnit;

// volatile 关键字 可见性的案例
public class VolatileSeeDemo {
    /**
     * t1	-------come in
     * main	 修改完成
     * t1	-------flag被设置为false，程序停止
     */
    static boolean flag = true;

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t-------come in");
            while (flag) {

            }
            System.out.println(Thread.currentThread().getName() + "\t-------flag被设置为false，程序停止");
        }, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //更新flag值
        flag = false;

        System.out.println(Thread.currentThread().getName() + "\t 修改完成");
    }
}
