package com.example.juc.book;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class TestTwoCompletableFuture {
    // 1.异步任务，返回 future
    public static CompletableFuture<String> doSomethingOne(String encodedCompanyId) {
        // 1.1 创建异步任务
        return CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                // 1.1 模拟计算
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 1.1.2 解密返回结果
                return encodedCompanyId;
            }
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1.创建 future 列表
        List<CompletableFuture<String>> futureList = new ArrayList<>();
        futureList.add(doSomethingOne("1"));
        futureList.add(doSomethingOne("2"));
        futureList.add(doSomethingOne("3"));
        futureList.add(doSomethingOne("4"));
        futureList.add(doSomethingOne("5"));
        futureList.add(doSomethingOne("6"));

        // 2.拼接多个 future 为同一个
        CompletableFuture<Void> result = CompletableFuture
                .allOf(futureList.toArray(new CompletableFuture[futureList.size()]));

        // 3.等待所有 future 都完成
        System.out.println(result.get());

    }
}
