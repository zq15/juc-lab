package com.example.juc.queue;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        // 第一组
//        System.out.println(blockingQueue.add("a"));
//        System.out.println(blockingQueue.add("b"));
//        System.out.println(blockingQueue.add("c"));
//        System.out.println(blockingQueue.element());

        // Exception in thread "main" java.lang.IllegalStateException: Queue full
//        System.out.println(blockingQueue.add("d"));

//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
        // Exception in thread "main" java.util.NoSuchElementException
//        System.out.println(blockingQueue.remove());


        // 第二组
//        System.out.println(blockingQueue.offer("a"));
//        System.out.println(blockingQueue.offer("b"));
//        System.out.println(blockingQueue.offer("c"));
//        // a
//        System.out.println(blockingQueue.peek());
//
//        // false
//        System.out.println(blockingQueue.offer("d"));
//
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        // null
//        System.out.println(blockingQueue.poll());

        // 第三组
//        blockingQueue.put("a");
//        blockingQueue.put("b");
//        blockingQueue.put("c");
//        // 会阻塞住 由于用的是定长的 ArrayBlockingQueue
////        blockingQueue.put("www");
//
//
//        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take());
//        // 阻塞
//        System.out.println(blockingQueue.take());

        // 第四组
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        // 阻塞 3S 后退出 返回 false
//        System.out.println(blockingQueue.offer("w", 3L, TimeUnit.SECONDS));


        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        // 阻塞 3S 后退出 返回 null
        System.out.println(blockingQueue.poll(3L, TimeUnit.SECONDS));

    }
}
