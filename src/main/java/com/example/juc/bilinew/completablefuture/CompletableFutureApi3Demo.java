package com.example.juc.bilinew.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class CompletableFutureApi3Demo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        System.out.println(CompletableFuture.supplyAsync(() -> "resultA")
        .thenRun(() -> {}).join()); // b需要a的结果，但是没有返回值
        System.out.println(CompletableFuture
        .supplyAsync(() -> "resultA").thenAccept(r-> System.out.println(r)).join()); // b需要a的结果，有返回值，但是没有返回值
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA")
        .thenApply(r -> r + " resultB").join()); // b需要a的结果，有返回值，有返回值

    }
}


