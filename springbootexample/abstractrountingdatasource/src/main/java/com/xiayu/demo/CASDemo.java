package com.xiayu.demo;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(10);
        int andIncrement = atomicInteger.getAndIncrement();

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        atomicBoolean.compareAndSet(false,true);

        AtomicLong atomicLong = new AtomicLong();
        atomicLong.addAndGet(10);
    }
}
