package com.example.juc.bilinew.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 需求：10个线程各自转账1000
 */
class BankAccount {
    public volatile int money = 0;
    // 创建一个AtomicIntegerFieldUpdater对象，用于更新money字段
    AtomicIntegerFieldUpdater<BankAccount> atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(BankAccount.class, "money");

    public void transferMoney(BankAccount bankAccount) {
        atomicIntegerFieldUpdater.getAndIncrement(bankAccount);

    }
}

public class AtomicIntegerFieldUpdaterDemo {
    public static void main(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 1000; j++) {
                        bankAccount.transferMoney(bankAccount);
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }, String.valueOf(i)).start();

        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + '\t' + "result: " + bankAccount.money); //main	result: 10000
    }
}
