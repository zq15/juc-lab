package com.example.juc.Collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * list 集合线程不安全
 */
public class ThreadDemo4 {

    public static void main(String[] args) {
        // 创建 ArrayList 集合
//        List<String> list = new ArrayList<>();
        // Vector 解决
//        List<String> list = new Vector<>();
//        Collections 工具类解决
//        List<String> list = Collections.synchronizedList(new ArrayList<>());

        List<String> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                // 向集合中添加内容
                list.add(UUID.randomUUID().toString().substring(0, 8));
                // 从集合获取内容
                System.out.println(list);
            }, String.valueOf(i)).start();
        }


        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                // 向集合中添加内容
                set.add(UUID.randomUUID().toString().substring(0, 8));
                // 从集合获取内容
                System.out.println(set);
            }, String.valueOf(i)).start();
        }

        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            String key = String.valueOf(i);
            new Thread(() -> {
                // 向集合中添加内容
                map.put(key, UUID.randomUUID().toString().substring(0, 8));
                // 从集合获取内容
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}
