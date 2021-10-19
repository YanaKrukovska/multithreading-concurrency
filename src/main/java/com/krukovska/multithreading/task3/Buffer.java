package com.krukovska.multithreading.task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Buffer {

    private static final Logger logger = LogManager.getLogger(Buffer.class);
    private static final Integer MAX_BUFFER = 20;

    private final Random random = new Random();
    private final List<Integer> messages = new ArrayList<>();

    public synchronized void consume() {
        while (messages.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info("Consumed message: {}", messages.remove(0));
        notifyAll();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public synchronized void produce() {
        while (messages.size() >= MAX_BUFFER) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int number = random.nextInt(100);
        logger.info("Produced new message: {}", number);
        messages.add(number);
        notifyAll();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
