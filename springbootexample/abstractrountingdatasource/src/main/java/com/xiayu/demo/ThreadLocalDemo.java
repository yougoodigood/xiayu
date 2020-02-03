package com.xiayu.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

@Slf4j
public class ThreadLocalDemo {

    private static ThreadLocal<Integer> count = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 5;
        }
    }; //cout的初始值是0

    private static ThreadLocal<Locale> language = new ThreadLocal<Locale>(){
        @Override
        protected Locale initialValue() {
            return Locale.CHINA;
        }
    };//languag的初始值为

    public static void main(String[] args) {

        Thread[] threads = new Thread[5];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {

                //language
                Locale lan = language.get();
                log.info(Thread.currentThread().getName() + ".language:" + lan);

                language.set(Locale.ENGLISH); //每个线程可针对具体场景设置该线程需要的值

                log.info(Thread.currentThread().getName() + ".language:" + language.get());

                //--------------------------------------------------------------------------------
                int num = count.get(); //每个线程获取count的一个拷贝
                num += 5;
                count.set(num); //进行加5,后再保存到线程里面
                //... 进行了别的业务操作

                count.get();//业务中需要获取到count的值
                log.info(Thread.currentThread().getName() + ":" + count.get()); //再获取到count的值

                count.remove();
                language.remove();
            }, "thread-" + i);
            threads[i].start();
        }
    }
}
