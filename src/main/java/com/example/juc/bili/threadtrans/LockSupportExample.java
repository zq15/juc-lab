package com.example.juc.bili.threadtrans;

import java.util.concurrent.locks.LockSupport;

/**
 * 使用 LockSupport 实现线程通信
 * 
 * LockSupport 提供了基于许可的线程阻塞和唤醒机制
 * 相比 wait/notify，LockSupport 不需要获取锁，不会导致死锁
 * park() 阻塞当前线程，unpark() 唤醒指定线程
 */
class LockSupportExample {
    private static Thread consumerThread;
    
    public static void main(String[] args) {
        // 创建消费者线程
        consumerThread = new Thread(() -> {
            System.out.println("Consumer: Starting and waiting for data...");
            
            // 阻塞当前线程，等待被唤醒
            LockSupport.park();
            
            System.out.println("Consumer: Woken up! Processing data...");
            
            try {
                Thread.sleep(1000);
                System.out.println("Consumer: Data processing completed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        // 创建生产者线程
        Thread producerThread = new Thread(() -> {
            System.out.println("Producer: Starting to produce data...");
            
            try {
                // 模拟数据生产过程
                Thread.sleep(2000);
                System.out.println("Producer: Data production finished, waking up consumer");
                
                // 唤醒消费者线程
                LockSupport.unpark(consumerThread);
                
                System.out.println("Producer: Consumer unparked, continuing work");
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        // 启动线程
        consumerThread.start();
        producerThread.start();
        
        // 等待线程结束
        try {
            producerThread.join();
            consumerThread.join();
            System.out.println("All threads completed");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}