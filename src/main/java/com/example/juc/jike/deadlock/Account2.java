package com.example.juc.jike.deadlock;

// 破坏循环等待条件
public class Account2 {
    private int id;
    private int balance;

    // 转账
    void transfer(Account2 target, int amt) {
        Account2 left = this;
        Account2 right = target;
        if (this.id > target.id) {
            left = target;
            right = this;
        }
        // 锁定序号小的账户
        synchronized (left) {
            // 锁定序号大的账户
            synchronized (right){
                if (this.balance > amt){
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }
    }
}
