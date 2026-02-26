package com.example.juc.bilinew.springasync;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * 全局异步异常处理器
 * 只对 void 返回值的 @Async 方法生效（Future/CompletableFuture 由调用方自己处理）
 */
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        System.out.println(">>> [AsyncUncaughtExceptionHandler] 捕获到异常");
        System.out.println("    方法: " + method.getName());
        System.out.println("    参数: " + java.util.Arrays.toString(params));
        System.out.println("    异常: " + ex.getMessage());
    }
}
