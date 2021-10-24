package com.krukovska.multithreading.task5;

import com.krukovska.multithreading.task5.model.Currency;
import com.krukovska.multithreading.task5.model.UserAccount;
import com.krukovska.multithreading.task5.service.UserAccountService;
import com.krukovska.multithreading.task5.service.impl.UserAccountServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.krukovska.multithreading.task5.model.Currency.*;
import static java.math.BigDecimal.valueOf;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class);
    public static final String TEST_USERNAME_1 = "John_Doe";
    public static final String TEST_USERNAME_2 = "Jane_Doe";
    public static final int THREAD_AMOUNT = 3;
    private final UserAccountService service;

    public App(UserAccountService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        App app = new App(new UserAccountServiceImpl());
        app.startDemo();
    }

    public void startDemo() {
        Map<Currency, BigDecimal> currencyMap = new EnumMap<>(Currency.class);
        currencyMap.put(UAH, valueOf(330));
        currencyMap.put(USD, valueOf(10));
        currencyMap.put(EUR, valueOf(0));
        service.save(new UserAccount(TEST_USERNAME_1, currencyMap));

        currencyMap.put(UAH, valueOf(0));
        currencyMap.put(USD, valueOf(1));
        currencyMap.put(EUR, valueOf(0));
        service.save(new UserAccount(TEST_USERNAME_2, currencyMap));

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_AMOUNT);

        executorService.submit(() -> service.exchangeCurrency(
                TEST_USERNAME_1, UAH, EUR, valueOf(330)));
        executorService.submit(() -> service.exchangeCurrency(
                TEST_USERNAME_1, UAH, EUR, valueOf(1000)));
        executorService.submit(() -> service.exchangeCurrency(
                TEST_USERNAME_2, USD, UAH, valueOf(1)));
        executorService.submit(() -> service.exchangeCurrency(
                TEST_USERNAME_2, USD, UAH, valueOf(20)));

        //Wait for threads to finish
        sleep();
        executorService.shutdown();

        logger.info("{} currencies: {}", TEST_USERNAME_1, service.findByUsername(TEST_USERNAME_1).getCurrencies());
        logger.info("{} currencies: {}", TEST_USERNAME_2, service.findByUsername(TEST_USERNAME_2).getCurrencies());

    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
