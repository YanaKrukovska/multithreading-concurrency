package com.krukovska.multithreading.task3;

import java.util.ArrayDeque;
import java.util.Queue;

public class MyQueue<T> {

    private final Queue<T> queue = new ArrayDeque<>();

    public T get() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        var element = queue.poll();
        System.out.println(Thread.currentThread().getName() + " отримано: " + element);
        notifyAll();
        return element;
    }

    public synchronized void put(T n) {
        while (!queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.add(n);
        System.out.println("Відправлено: " + n);
        notifyAll();
    }
}

