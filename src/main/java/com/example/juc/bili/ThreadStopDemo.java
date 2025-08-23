package com.example.juc.bili;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 线程停止方式演示类
 * 演示Java中停止线程的所有方式
 */
public class ThreadStopDemo {

    /**
     * 方式1：使用标志位停止线程
     */
    static class FlagStopThread extends Thread {
        private boolean running = true;

        public void stopThread() {
            running = false;
        }

        @Override
        public void run() {
            System.out.println("标志位线程开始运行...");
            while (running) {
                try {
                    System.out.println("标志位线程正在运行...");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("标志位线程被中断");
                    break;
                }
            }
            System.out.println("标志位线程已停止");
        }
    }

    /**
     * 方式2：使用interrupt()停止线程
     */
    static class InterruptStopThread extends Thread {
        @Override
        public void run() {
            System.out.println("中断线程开始运行...");
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println("中断线程正在运行...");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("中断线程捕获到中断异常");
                    // 重新设置中断状态
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println("中断线程已停止");
        }
    }

    /**
     * 方式3：使用volatile变量停止线程
     */
    static class VolatileStopThread extends Thread {
        private volatile boolean running = true;

        public void stopThread() {
            running = false;
        }

        @Override
        public void run() {
            System.out.println("volatile线程开始运行...");
            while (running) {
                try {
                    System.out.println("volatile线程正在运行...");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("volatile线程被中断");
                    break;
                }
            }
            System.out.println("volatile线程已停止");
        }
    }

    /**
     * 方式4：使用AtomicBoolean停止线程
     */
    static class AtomicStopThread extends Thread {
        private final AtomicBoolean running = new AtomicBoolean(true);

        public void stopThread() {
            running.set(false);
        }

        @Override
        public void run() {
            System.out.println("AtomicBoolean线程开始运行...");
            while (running.get()) {
                try {
                    System.out.println("AtomicBoolean线程正在运行...");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("AtomicBoolean线程被中断");
                    break;
                }
            }
            System.out.println("AtomicBoolean线程已停止");
        }
    }

    /**
     * 方式5：使用废弃的stop()方法（仅作演示，不推荐使用）
     */
    static class DeprecatedStopThread extends Thread {
        @Override
        public void run() {
            System.out.println("废弃stop方法线程开始运行...");
            try {
                while (true) {
                    System.out.println("废弃stop方法线程正在运行...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("废弃stop方法线程被中断");
                        break;
                    }
                }
            } catch (ThreadDeath e) {
                System.out.println("废弃stop方法线程被强制停止");
                // 清理资源
                throw e;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("===== 线程停止方式演示 =====");

        // 测试方式1：标志位
        System.out.println("\n--- 方式1：标志位停止 ---");
        FlagStopThread flagThread = new FlagStopThread();
        flagThread.start();
        Thread.sleep(3000);
        flagThread.stopThread();
        flagThread.join();

        // 测试方式2：interrupt()
        System.out.println("\n--- 方式2：interrupt()停止 ---");
        InterruptStopThread interruptThread = new InterruptStopThread();
        interruptThread.start();
        Thread.sleep(3000);
        interruptThread.interrupt();
        interruptThread.join();

        // 测试方式3：volatile变量
        System.out.println("\n--- 方式3：volatile变量停止 ---");
        VolatileStopThread volatileThread = new VolatileStopThread();
        volatileThread.start();
        Thread.sleep(3000);
        volatileThread.stopThread();
        volatileThread.join();

        // 测试方式4：AtomicBoolean
        System.out.println("\n--- 方式4：AtomicBoolean停止 ---");
        AtomicStopThread atomicThread = new AtomicStopThread();
        atomicThread.start();
        Thread.sleep(3000);
        atomicThread.stopThread();
        atomicThread.join();

        // 测试方式5：废弃的stop()方法（仅作演示）
        System.out.println("\n--- 方式5：废弃的stop()方法（不推荐使用）---");
        DeprecatedStopThread deprecatedThread = new DeprecatedStopThread();
        deprecatedThread.start();
        Thread.sleep(3000);
        // 使用废弃的stop()方法（不安全，仅作演示）
        deprecatedThread.stop();
        try {
            deprecatedThread.join();
        } catch (Exception e) {
            System.out.println("捕获到stop()方法抛出的异常");
        }

        System.out.println("\n===== 所有线程停止方式演示完成 =====");
    }
}