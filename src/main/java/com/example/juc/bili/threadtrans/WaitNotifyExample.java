package com.example.juc.bili.threadtrans;

/**
 * 使用 Object.wait / notify. notifyAll
 *
 * notify 随机唤醒一个线程，如果这个线程没有结束之前之心 notify 那么其他阻塞线程只能等待超时或者中断
 * notifyAll 唤醒所有线程，开始竞争锁，竞争到锁的线程执行完后，又会开始唤醒其他所有线程开始新的一轮锁竞争
 */
class WaitNotifyExample {
    private static final Object lock = new Object();

    public static void main(String[] args) {
        // 生产者线程
        Thread producer = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("Producer: Producing...");
                    Thread.sleep(2000);
                    System.out.println("Producer: Production finished. Notifying consumer.");
                    // 唤醒等待的线程
                    lock.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 消费者线程
        Thread consumer = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("Consumer: Waiting for production to finish.");
                    // 进入等待状态
                    lock.wait();
                    System.out.println("Consumer: Production finished. Consuming...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        consumer.start();
        producer.start();
    }
}