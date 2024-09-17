package com.example.juc.bilinew.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;  

/**
 * 这个类演示了AtomicInteger的使用，主要包含以下几个要点：
 * 1. MyNumber类：封装了一个AtomicInteger对象，提供了addPlusPlus方法来原子性地增加计数。
 * 2. AtomicIntegerDemo类：
 *    - 使用CountDownLatch来协调多个线程的完成
 *    - 创建50个线程，每个线程对计数器增加10次
 *    - 等待所有线程完成后，输出最终结果
 * 3. 线程安全：通过使用AtomicInteger，确保了多线程环境下的计数操作是线程安全的
 * 4. 并发性能：利用CAS（Compare-And-Swap）操作，避免了使用synchronized的性能开销
 * 5. 结果验证：最终输出应该是500，证明了AtomicInteger在高并发情况下的正确性
 */
class MyNumber {
    AtomicInteger atomicInteger = new AtomicInteger();

    public void addPlusPlus() {
        atomicInteger.getAndIncrement();
    }

}

public class AtomicIntegerDemo {

    public static final int SIZE = 5000;

    public static void main(String[] args) throws InterruptedException {
        MyNumber myNumber = new MyNumber();
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);
        for (int i = 1; i <= SIZE; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 10; j++) {
                        myNumber.addPlusPlus();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }, String.valueOf(i)).start();

        }
        countDownLatch.await();

        System.out.println(Thread.currentThread().getName() + "\t" + "result: " + myNumber.atomicInteger.get());//main	result: 500
    }
}

