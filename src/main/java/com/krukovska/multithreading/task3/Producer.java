package com.krukovska.multithreading.task3;

public class Producer implements Runnable {


    private final Buffer buffer;


    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            buffer.produce();
        }
    }

}
