package com.example.juc.bili.threadtrans;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 使用管道输入输出流实现线程通信
 * 
 * PipedInputStream 和 PipedOutputStream 提供了线程间的数据管道通信方式
 * 一个线程写入数据到管道输出流，另一个线程从管道输入流读取数据
 */
class PipedStreamExample {
    public static void main(String[] args) {
        try {
            // 创建管道输入输出流
            PipedInputStream pipedInputStream = new PipedInputStream();
            PipedOutputStream pipedOutputStream = new PipedOutputStream();
            
            // 连接管道
            pipedInputStream.connect(pipedOutputStream);
            
            // 创建生产者线程
            Thread producer = new Thread(() -> {
                try {
                    System.out.println("Producer: Starting to write data...");
                    
                    // 写入多条消息
                    String[] messages = {
                        "Hello from producer!",
                        "This is message 2",
                        "Final message before closing"
                    };
                    
                    for (String message : messages) {
                        byte[] data = message.getBytes();
                        pipedOutputStream.write(data);
                        pipedOutputStream.write('\n'); // 添加换行符分隔消息
                        System.out.println("Producer: Wrote - " + message);
                        Thread.sleep(1000); // 模拟处理时间
                    }
                    
                    // 关闭输出流，通知消费者结束
                    pipedOutputStream.close();
                    System.out.println("Producer: Closed output stream");
                    
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
            
            // 创建消费者线程
            Thread consumer = new Thread(() -> {
                try {
                    System.out.println("Consumer: Starting to read data...");
                    
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    StringBuilder messageBuilder = new StringBuilder();
                    
                    // 循环读取数据
                    while ((bytesRead = pipedInputStream.read(buffer)) != -1) {
                        String chunk = new String(buffer, 0, bytesRead);
                        messageBuilder.append(chunk);
                        
                        // 按行处理消息
                        String[] messages = messageBuilder.toString().split("\n");
                        
                        // 处理完整的消息行
                        for (int i = 0; i < messages.length - 1; i++) {
                            if (!messages[i].isEmpty()) {
                                System.out.println("Consumer: Read - " + messages[i]);
                            }
                        }
                        
                        // 保留未完成的消息部分
                        if (!chunk.endsWith("\n")) {
                            messageBuilder = new StringBuilder(messages[messages.length - 1]);
                        } else {
                            messageBuilder = new StringBuilder();
                        }
                    }
                    
                    // 处理最后剩余的消息
                    if (messageBuilder.length() > 0) {
                        System.out.println("Consumer: Read - " + messageBuilder.toString());
                    }
                    
                    // 关闭输入流
                    pipedInputStream.close();
                    System.out.println("Consumer: Closed input stream");
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            
            // 启动线程
            consumer.start();
            producer.start();
            
            // 等待线程结束
            producer.join();
            consumer.join();
            
            System.out.println("All threads completed");
            
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}