package com.example.juc.bilinew.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 需求：多线程并发调用一个类的初始化方法，如果未被初始化过，将执行初始化工作
 * 要求只能被初始化一次，只有一个线程操作成功
 */
public class AtomicReferenceFieldUpdaterDemo {

//    public static void main(String[] args) {
//        MyVar myVar = new MyVar();
//        for (int i = 1; i <= 5; i++) {
//            new Thread(() -> {
//                myVar.init(myVar);
//            }, String.valueOf(i)).start();
//        }
//    }
}
/**
 * 1	--------------start init ,need 2 secondes
 * 5	--------------已经有线程进行初始化工作了。。。。。
 * 2	--------------已经有线程进行初始化工作了。。。。。
 * 4	--------------已经有线程进行初始化工作了。。。。。
 * 3	--------------已经有线程进行初始化工作了。。。。。
 * 1	--------------over init
 */
