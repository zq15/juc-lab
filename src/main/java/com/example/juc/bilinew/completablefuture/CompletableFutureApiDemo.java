package com.example.juc.bilinew.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletableFutureApiDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "abc";
        });

        // 暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        System.out.println(completableFuture.get());
//        System.out.println(completableFuture.get(2L, TimeUnit.SECONDS)); // 过时不候
//        System.out.println(completableFuture.join());

       System.out.println(completableFuture.getNow("0")); // 没有计算完成，返回缺省值

    //    System.out.println(completableFuture.complete("completeValue")+"\t" +completableFuture.join()); // 在 getNow 的基础上能够拿到当前执行状态，返回 true 或者 false
    }
}