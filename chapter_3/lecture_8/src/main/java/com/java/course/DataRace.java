package com.java.course;

import java.util.concurrent.atomic.AtomicLong;

public class DataRace {

    public static void main(String[] args) {
        Shared shared = new Shared();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                shared.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                shared.checkDataRace();
            }

        });

        thread1.start();
        thread2.start();
    }

    public static class Shared{
        //The values in the AtomicLong object are volatile
        private AtomicLong x = new AtomicLong(0L);
        private AtomicLong y = new AtomicLong(0L);

        public void increment(){
            //x is increased in a thread-safe way and
            //so does y.
            //x should be increased before y.
            //So, normally x should be always greater than y.
             x.incrementAndGet();
             y.incrementAndGet();
        }

        public void checkDataRace(){
            if (x.get()<y.get()){
                System.out.println("y>x is a data race condition");
            }
        }

    }
}
