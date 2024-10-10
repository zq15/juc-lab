package com.example.juc.bili.pool;

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueExample {
    public static void main(String[] args) {
        // 创建一个 SynchronousQueue 队列
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        
        // 创建生产者线程
        Thread producer = new Thread(() -> {
            try {
                System.out.println("Producer is waiting to put an element...");
                queue.put("Hello, World!");
                System.out.println("Producer has put an element.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 创建消费者线程
        Thread consumer = new Thread(() -> {
            try {
                System.out.println("Consumer is waiting to take an element...");
                String element = queue.take();
                System.out.println("Consumer has taken: " + element);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 启动生产者和消费者线程
        producer.start();
        consumer.start();
    }
}
