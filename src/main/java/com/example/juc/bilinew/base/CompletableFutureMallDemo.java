package com.example.juc.bilinew.base;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import lombok.Getter;

// 电商比价案例
public class CompletableFutureMallDemo {
    static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("dangdang"),
            new NetMall("taobao"),
            new NetMall("pdd"),
            new NetMall("tmall")
    );

    public static List<String> getPrice(List<NetMall> list, String productName) {
        return list.stream()
                .map(netMall ->
                    String.format(productName + " in %s price is %.2f",
                            netMall.getNetMallName(),
                            netMall.calcPrice(productName)))
                .collect(Collectors.toList());
    }

    public static List<String> getPriceByCompletableFuture(List<NetMall> list, String productName) {
        return list.stream()
                .map(netMall -> CompletableFuture.supplyAsync(()->
                    String.format(productName + " in %s price is %.2f",
                            netMall.getNetMallName(),
                            netMall.calcPrice(productName)))
                ).collect(Collectors.toList()).stream().map(s -> s.join()).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        long startTime1 = System.currentTimeMillis();
        List<String> list1 = getPrice(CompletableFutureMallDemo.list, "mysql");
        for (String element : list1) {
            System.out.println(element);
        }
        long endTime1 = System.currentTimeMillis();
        System.out.println("------costTime: " + (endTime1 - startTime1) + " 毫秒");

        System.out.println("-----------------------------------");

        long startTime2 = System.currentTimeMillis();
        List<String> list2 = getPriceByCompletableFuture(CompletableFutureMallDemo.list, "mysql");
        for (String element : list2) {
            System.out.println(element);
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("------costTime: " + (endTime2 - startTime2) + " 毫秒");
    }
}

class NetMall {
    @Getter
    private String netMallName;

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }

    public double calcPrice(String productName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }
}