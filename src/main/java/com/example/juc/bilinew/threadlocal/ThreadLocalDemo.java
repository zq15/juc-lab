 package com.example.juc.bilinew.threadlocal;

 /**
  * 不同线程读写同一个局部变量，存在线程安全问题
  */
 public class ThreadLocalDemo {
     private String content;

     private String getContent() {
         return content;
     }

     private void setContent(String content) {
         this.content = content;
     }

     public static void main(String[] args) {
         ThreadLocalDemo demo = new ThreadLocalDemo();
         for (int i = 0; i < 5; i++) {
             Thread thread = new Thread(new Runnable() {
                 @Override
                 public void run() {
                     demo.setContent(Thread.currentThread().getName() + "的数据");
                     System.out.println(Thread.currentThread().getName() + "--->" + demo.getContent());
                 }
             });
             thread.setName("线程" + i);
             thread.start();
         }
     }
 }