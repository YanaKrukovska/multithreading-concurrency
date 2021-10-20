package com.krukovska.multithreading.task1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ConcurrentModificationException;
import java.util.Map;

public class MapTester {

    private static final Logger logger = LogManager.getLogger(MapTester.class);
    private static final int MAX_INPUT = 1000;
    private final String mapName;
    private final Map<Integer, Integer> map;
    private boolean isStopped = false;

    public MapTester(String mapName, Map<Integer, Integer> map) {
        this.mapName = mapName;
        this.map = map;
    }

    public void start() {
        Thread addNumbersThread = new Thread(() -> {
            int i = 0;
            while (!isStopped) {
                if (i == MAX_INPUT) {
                    isStopped=true;
                }
                map.put(i, i);
                logger.info("Put number {} to map", i);
                i++;
                sleepRandomAmountOfTime();
            }
        });


        Thread sumNumbersThread = new Thread(() -> {
            while (!isStopped) {
                try {
                    logger.info("Sum = {}", map.values().stream().reduce(Integer::sum).orElse(0));
                    sleepRandomAmountOfTime();
                } catch (ConcurrentModificationException e) {
                    e.printStackTrace();
                    isStopped = true;
                    break;
                }
            }
        });

        addNumbersThread.setName(mapName);
        sumNumbersThread.setName(mapName);
        addNumbersThread.start();
        sumNumbersThread.start();

        try {
            addNumbersThread.join();
            sumNumbersThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sleepRandomAmountOfTime() {
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
