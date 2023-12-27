package com.example.juc.jike.scope;

/**
 * @author zhouzhou
 */
public class Account {
    // 锁：保护账户余额
    private final Object bakLock = new Object();

    // 账户余额
    private Integer balance;

    // 锁：保护账户密码
    private final Object pwLock = new Object();

    // 账户密码
    private String password;

    // 取款
    void withDraw(Integer amt) {
        synchronized (bakLock) {
            if (this.balance > amt) {
                this.balance -= amt;
            }
        }
    }

    // 查看余额
    Integer getBalance() {
        synchronized (bakLock) {
            return balance;
        }
    }

    // 更改密码
    void updatePassword(String pw) {
        synchronized (pwLock) {
            this.password = pw;
        }
    }

    // 查看密码
    String getPassword(){
        synchronized (pwLock){
            return password;
        }
    }

    // 转账A 只能锁 this 无法锁定 target
    synchronized void transferA(Account target, int amt){
        if (this.balance > amt) {
            this.balance -= amt;
            target.balance += amt;
        }
    }

    // 转账B 只能锁 this 无法锁定 target
    void transferB(Account target, int amt){
        synchronized (Account.class){
            if (this.balance > amt) {
                this.balance -= amt;
                target.balance += amt;
            }
        }
    }

    // 转账C 细粒度锁处理 -> 存在死锁问题
    void transferC(Account target, int amt){
        // 锁定转入客户
        synchronized (this){
            // 锁定转出客户
            synchronized (target){
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }
    }
}
