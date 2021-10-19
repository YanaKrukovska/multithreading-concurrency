package com.krukovska.multithreading.task3;

/*
    Implement message bus using Producer-Consumer pattern.

    1. Implement asynchronous message bus. Do not use queue implementations from java.util.concurrent.
    2. Implement producer, which will generate and post randomly messages to the queue.
    3. Implement consumer, which will consume messages on specific topic and log to the console message payload.
    4. (Optional) Application should create several consumers and producers that run in parallel.
 */

public class Task3 {

    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        Thread t1 = new Thread(new Producer(buffer), "Producer 1");
        Thread t2 = new Thread(new Producer(buffer), "Producer 2");
        Thread t3 = new Thread(new Consumer(buffer), "Consumer 1");
        Thread t4 = new Thread(new Consumer(buffer), "Consumer 2");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}

