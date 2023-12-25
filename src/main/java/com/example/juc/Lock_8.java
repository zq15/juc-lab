package com.example.juc;

import java.util.concurrent.TimeUnit;

class Phone {


    public static synchronized void sendSMS() throws Exception {
        // 停留 4 秒
//        TimeUnit.SECONDS.sleep(4);
        System.out.println("------sendSMS");
    }

    public static synchronized void sendEmail() throws Exception {
        System.out.println("------sendEmail");
    }

    public void getHello() throws Exception {
        // 停留 4 秒
        System.out.println("------getHello");
    }

}

/**
 * 1 标准访问，先打印短信还是邮件
 * ------sendSMS
 * ------sendEmail
 * <p>
 * 2 停 4 秒在短信方法，先打印短信还是邮件
 * ------sendSMS
 * ------sendEmail
 *
 * 前两种都是锁当前对象
 * <p>
 * 3 新增普通的 hello 方法，是先打短信 还是 hello
 * ------getHello
 * ------sendSMS
 * <p>
 * 4 现在有两部 手机，先打印短信还是邮件
 * ------sendEmail
 * ------sendSMS
 *
 * 用的不是同一把锁
 * <p>
 * 5 两个静态同步方法，1部手机，先打印短信还是邮件
 * ------sendSMS
 * ------sendEmail
 *
 * 静态 锁 class
 * <p>
 * 6 两个静态同步方法，2部手机，先打印短信还是邮件
 * ------sendSMS
 * ------sendEmail
 * <p>
 * 7 1个静态同步方法，1个普通同步方法，1部手机，先打印短信还是邮件
 * <p>
 * ------sendEmail
 * ------sendSMS
 * <p>
 * 8 1个静态同步方法，1个普通同步方法，2部手机，先打印短信还是邮件
 */
public class Lock_8 {

    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();
        Phone phone2 = new Phone();


        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "AA").start();


        Thread.sleep(100);

        new Thread(() -> {
            try {
                phone.sendEmail();
//                phone.getHello();

//                phone2.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "BB").start();

    }
}
