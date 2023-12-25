package com.example.juc.bili.tools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// 集齐七颗龙珠可召唤神龙
public class CyclicBarrierDemo {

    private static final int NUMBER = 7;

    public static void main(String[] args) {
        // 创建固定值
        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMBER, () -> {
            System.out.println("******集齐七颗龙珠可召唤神龙");
        });

        // 集齐七颗龙珠过程
        for (int i = 1; i <= 7; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "星龙珠被收集到了");
                // 等待
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
