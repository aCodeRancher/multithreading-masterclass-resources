package com.java.course;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args){
        Thread t1 = new Thread( () -> {
             for (int i=0;i<10;i++){
                 atomicInteger.incrementAndGet();
             }
         }
       );
       Thread t2 = new Thread( () -> {
            for (int i=0;i<10;i++){
                atomicInteger.incrementAndGet();
            }
        });
        Thread t3 = new Thread( () -> {
            for (int i=0;i<10;i++){
                atomicInteger.incrementAndGet();
            }
        });

        t1.start();
        t2.start();
        t3.start();
       try {
           t1.join();
           t2.join();
           t3.join();
       }
        catch(InterruptedException e){}
      System.out.println("The number " + atomicInteger.get());
    }
}
