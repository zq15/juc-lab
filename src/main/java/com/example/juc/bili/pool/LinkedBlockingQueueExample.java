package com.example.juc.bili.pool;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueExample {
    public static void main(String[] args) {
        // 创建一个容量为 5 的 LinkedBlockingQueue 队列
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(5);
        
        // 创建生产者线程
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    String element = "Element-" + i;
                    System.out.println("Producer is putting: " + element);
                    queue.put(element);  // 阻塞直到有空间可放入元素
                    System.out.println("Producer has put: " + element);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 创建消费者线程
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    System.out.println("Consumer is waiting to take an element...");
                    String element = queue.take();  // 阻塞直到有元素可取
                    System.out.println("Consumer has taken: " + element);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 启动生产者和消费者线程
        producer.start();
        consumer.start();
    }
}
