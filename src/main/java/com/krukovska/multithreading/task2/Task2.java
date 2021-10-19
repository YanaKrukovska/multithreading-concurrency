package com.krukovska.multithreading.task2;

/*
 * Create three threads:
 * 1st thread is infinitely writing random number to the collection;
 * 2nd thread is printing sum of the numbers in the collection;
 * 3rd is printing square root of sum of squares of all numbers in the collection.
 * Make these calculations thread-safe using synchronization block. Fix the possible deadlock.
 */

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static java.lang.Math.sqrt;

public class Task2 {

    private static final Logger logger = LogManager.getLogger(Task2.class);
    private static final List<Integer> numbers = new LinkedList<>();
    private static final Random random = new Random();

    public static void main(String[] args) {

        Thread randomThread = new Thread(() -> {
            while (true) {
                synchronized (numbers) {
                    int randomNumber = random.nextInt(10);
                    numbers.add(randomNumber);
                    logger.info("Added number {}", randomNumber);
                }
                sleep();
            }
        });

        Thread sumThread = new Thread(() -> {
            while (true) {
                synchronized (numbers) {
                    logger.info("Sum = {}", numbers.stream().mapToInt(Integer::intValue).sum());
                }
                sleep();
            }
        });

        Thread squareThread = new Thread(() -> {
            while (true) {
                synchronized (numbers) {
                    logger.info("Square = {}", sqrt(numbers.stream().mapToInt(Integer::intValue).sum()));
                }
                sleep();
            }
        });

        randomThread.start();
        sumThread.start();
        squareThread.start();
    }

    private static void sleep(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
