package com.example.juc.jike.deadlock;

// 破坏占用且等待
public class Account1 {
    // actr 应该是单例
    private Allocator actr;
    private int balance;

    // 转账
    void transfer(Account1 target, int amt){
    // 一次性申请转出账户和转入账户，直到成功
        while(!actr.apply(this, target)) {
            ;
        }
        try{
            // 锁定转出账户
            synchronized(this){
                // 锁定转入账户
                synchronized(target){
                    if (this.balance > amt){
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        } finally {
            actr.free(this, target);
        }
    }
}
