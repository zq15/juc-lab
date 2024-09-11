package com.example.juc.bilinew.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

// 结果合并案例
public class CompletableFutureCombine {
    public static void main(String[] args) {
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "hello supplyAsync1";
        });

        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "hello supplyAsync2";
        });

        CompletableFuture<String> completableFuture3 = completableFuture1.thenCombine(completableFuture2, (r1, r2) -> {
            System.out.println("开始结果合并");
            return r1 + " " + r2;
        });

        System.out.println(completableFuture3.join());
    }
}
