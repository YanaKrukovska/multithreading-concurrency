package com.krukovska.multithreading.task4;

import java.util.Random;

/*
Create simple object pool with support for multithreaded environment. No any extra inheritance, polymorphism
 or generics needed here, just implementation of simple class:
 */
public class Task4 {

    public static void main(String[] args) {

        BlockingObjectPool blockingPool = new BlockingObjectPool(3);

        Thread getThread = new Thread(() -> {
            while (true) {
                blockingPool.get();
                sleepRandom();
            }
        });

        Thread putThread = new Thread(() -> {
            while (true) {
                blockingPool.put(new Random().nextInt(10));
                sleepRandom();
            }
        });

        getThread.start();
        putThread.start();

    }

    private static void sleepRandom() {
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
