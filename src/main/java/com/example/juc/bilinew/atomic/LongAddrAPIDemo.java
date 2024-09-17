package com.example.juc.bilinew.atomic;

import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.atomic.LongAccumulator;

public class LongAddrAPIDemo {
    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        longAdder.increment();
        longAdder.increment();
        System.out.println(longAdder.longValue());

        LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);
        longAccumulator.accumulate(1);
        longAccumulator.accumulate(3);
        System.out.println(longAccumulator.get());
    }   
}
