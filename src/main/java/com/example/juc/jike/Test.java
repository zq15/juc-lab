package com.example.juc.jike;

/**
 * 测试内存可见性问题
 */
public class Test {

    private long count = 0;

    private void add100k() {
        int idx = 0;
        while (idx++ < 100000) {
            count += 1;
        }
    }

    public static long calc() throws InterruptedException {
        final Test test = new Test();
        // 创建两个线程，执行 add() 操作
        Thread th1 = new Thread(() -> {
            test.add100k();
        });
        Thread th2 = new Thread(() -> {
            test.add100k();
        });
        // 启动两个线程
        th1.start();
        th2.start();
        // 等待两个线程执行结束
        th1.join();
        th2.join();
        return test.count;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(calc());
    }
}
