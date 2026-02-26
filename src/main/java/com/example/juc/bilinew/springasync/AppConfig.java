package com.example.juc.bilinew.springasync;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 不注册自定义 AsyncUncaughtExceptionHandler
 * Spring 默认使用 SimpleAsyncUncaughtExceptionHandler，对 void 方法的异常只打日志
 *
 * 如果不开启 @EnableAsync 相当于串行
 */
@Configuration
@EnableAsync
@ComponentScan("com.example.juc.bilinew.springasync")
public class AppConfig {
}
