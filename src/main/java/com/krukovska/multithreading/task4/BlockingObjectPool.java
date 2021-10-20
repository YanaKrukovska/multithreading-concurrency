package com.krukovska.multithreading.task4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Pool that block when it has not any items or it full
 */

public class BlockingObjectPool {

    private static final Logger logger = LoggerFactory.getLogger(BlockingObjectPool.class);

    private final List<Object> pool;
    private final int size;

    /**
     * Creates filled pool of passed size
     *
     * @param size of pool
     */
    public BlockingObjectPool(int size) {
        this.pool = new ArrayList<>(size);
        this.size = size;

        for (int i = 0; i < size; i++) {
            pool.add(i);
        }
    }

    /**
     * Gets object from pool or blocks if pool is empty
     *
     * @return object from pool
     */
    public synchronized Object get() {
        while (pool.isEmpty()) {
            logger.info("Pool is empty. Blocking");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Object result = pool.remove(pool.size() - 1);
        notifyAll();
        logger.info("Got {}", result);
        return result;
    }

    /**
     * Puts object to pool or blocks if pool is full
     *
     * @param object to be taken back to pool
     */
    public synchronized void put(Object object) {
        while (pool.size() >= size) {
            logger.info("Pool is full. Blocking");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        pool.add(object);
        logger.info("Put {}", object);
        notifyAll();
    }
}
