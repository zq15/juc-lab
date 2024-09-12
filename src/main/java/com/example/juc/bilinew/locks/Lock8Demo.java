package com.example.juc.bilinew.locks;

import java.util.concurrent.TimeUnit;

class Phone {
    public synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sendEmail");
    }

    public synchronized void sendSMS() {
        System.out.println("sendSMS");
    }

    public void hello() {
        System.out.println("hello");
    }
}

// 题目，谈谈你对多线程锁的理解，8锁案例说明
/**
 * 口诀，线程，操作，资源类
 * 8锁案例说明
 * 1.标准访问，先打印邮件还是短信？ 邮件 -> 邮件先获取锁，然后是短信
 * 2.邮件方法暂停4秒钟，先打印邮件还是短信？ 邮件 -> 虽然邮件方法内部暂停，但因为用的同一把锁，所以短信需要等待邮件释放锁
 * 3.新增普通hello方法，先打印邮件还是hello？ 邮件 -> 邮件方法先执行，两个方法互相无影响
 * 4.两部手机，先打印邮件还是短信？ 短信 -> 两部手机，两把锁，互不影响
 * 5.两个静态同步方法，同一部手机，先打印邮件还是短信？ 邮件 -> 静态方法，锁的是class，两个方法用的是同一把锁，所以邮件先执行
 * 6.两个静态同步方法，两部手机，先打印邮件还是短信？ 邮件 -> 静态方法，锁的是class，两个方法用的是同一把锁，所以邮件先执行
 * 7.一个静态同步方法，一个普通同步方法，同一部手机，先打印邮件还是短信？ 短信 -> 静态方法，锁的是class，普通方法，锁的是this，两个方法用的是不同的锁，所以短信先执行
 * 8.一个静态同步方法，一个普通同步方法，两部手机，先打印邮件还是短信？ 短信 -> 静态方法，锁的是class，普通方法，锁的是this，两个方法用的是不同的锁，所以短信先执行
 */
public class Lock8Demo {
    public static void main(String[] args) {
        Phone phone = new Phone();
        Phone phone2 = new Phone();
        new Thread(() -> {
            phone.sendEmail();
        }, "A").start();
        
        // 暂停200毫秒，保证a线程先启动
        try {
            TimeUnit.MICROSECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            phone.sendSMS();
            // phone.hello();
            // phone2.sendSMS();
        }, "B").start();
    }
}
