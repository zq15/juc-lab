package com.example.juc.bilinew.completablefuture.future;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;


public class FutureTaskDemo {
    public static void main(String[] args) {
        FutureTask futureTask = new FutureTask(new MyThread2());
        new Thread(futureTask).start();
        try {
            System.out.println(futureTask.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MyThread1 implements Runnable {
    @Override
    public void run() {
        System.out.println("MyThread1");
    }
}

class MyThread2 implements Callable {
    @Override
    public Integer call() throws Exception {
        System.out.println("MyThread2");
        return 1024;
    }
}