// package com.example.juc.bilinew.threadlocal;

// import java.util.Random;
// import java.util.concurrent.TimeUnit;

// /**
//  * 需求：5个销售卖房子，集团只关心销售总量的精确统计数
//  */
// class House {
//     int saleCount = 0;

//     public synchronized void saleHouse() {
//         saleCount++;
//     }

// }

// public class ThreadLocalDemo {
//     public static void main(String[] args) {
//         House house = new House();
//         for (int i = 0; i < 5; i++) {
//             new Thread(() -> {
//                 int size = new Random().nextInt(5) + 1;
//                 System.out.println(size);
//                 for (int j = 1; j <= size; j++) {
//                     house.saleHouse();
//                 }
//             }, String.valueOf(i)).start();
//         }

//         try {
//             TimeUnit.MILLISECONDS.sleep(300);
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }
//         System.out.println(Thread.currentThread().getName() + "\t" + "共计卖出多少套： " + house.saleCount);
//     }
// }
