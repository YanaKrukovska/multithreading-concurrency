package com.krukovska.multithreading.task5.repository;

import com.krukovska.multithreading.task5.model.Currency;
import com.krukovska.multithreading.task5.model.UserAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

import static com.krukovska.multithreading.task5.model.Currency.valueOf;

public class UserAccountRepository {

    private static final Logger logger = LogManager.getLogger(UserAccountRepository.class);
    public static final String TXT_EXTENSION = ".txt";

    public UserAccount getAccountByUsername(String username) {
        logger.info("Searching for account for username={}", username);

        Map<Currency, BigDecimal> currencyMap = new EnumMap<>(Currency.class);

        try (Scanner scanner = new Scanner(new FileReader(username + TXT_EXTENSION))) {

            while (scanner.hasNextLine()) {
                String[] splitLine = scanner.nextLine().split(" ");
                currencyMap.put(valueOf(splitLine[0]), new BigDecimal(splitLine[1]));
            }

        } catch (FileNotFoundException e) {
            logger.info("Error while reading account", e);
            return null;
        }

        return new UserAccount(username, currencyMap);
    }

    public UserAccount save(UserAccount userAccount) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(userAccount.getUsername() + TXT_EXTENSION))) {
            userAccount.getCurrencies().forEach((key, value) -> printWriter.println(key + " " + value + " "));
        } catch (IOException e) {
            logger.info("Error while printing account", e);
        }
        logger.info("Saved account {}", userAccount);
        return userAccount;
    }
}
