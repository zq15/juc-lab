package com.example.juc.bilinew.locks;

public class LockSyncDemo {

    Object object = new Object();

    // public void m1() {
    //     synchronized (object) {
    //         System.out.println("---m1---");
    //     }
    // }
    
    // public synchronized void m2() {
    //     System.out.println("---m2---");
    // }

    public static synchronized void m3() {
        System.out.println("---m3---");
    }

    public static void main(String[] args) {
        
    }
}
