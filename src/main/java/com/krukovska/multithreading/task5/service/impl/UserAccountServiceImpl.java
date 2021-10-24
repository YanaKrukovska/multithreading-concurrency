package com.krukovska.multithreading.task5.service.impl;

import com.krukovska.multithreading.task5.exception.InsufficientAmountException;
import com.krukovska.multithreading.task5.exception.UserNotFoundException;
import com.krukovska.multithreading.task5.model.Currency;
import com.krukovska.multithreading.task5.model.UserAccount;
import com.krukovska.multithreading.task5.repository.UserAccountRepository;
import com.krukovska.multithreading.task5.service.UserAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Map;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_EVEN;

public class UserAccountServiceImpl implements UserAccountService {

    private static final Logger logger = LogManager.getLogger(UserAccountServiceImpl.class);
    private final UserAccountRepository repository = new UserAccountRepository();

    @Override
    public UserAccount findByUsername(String username) {
        return repository.getAccountByUsername(username);
    }

    @Override
    public UserAccount save(UserAccount account) {
        return repository.save(account);
    }

    @Override
    public synchronized UserAccount exchangeCurrency(String username, Currency from, Currency to, BigDecimal amount) {
        UserAccount userAccount = repository.getAccountByUsername(username);

        if (userAccount == null) {
            logger.info("User with username {} does not exist", username);
            throw new UserNotFoundException("User does not exist");
        }

        logger.info("Start exchange operation for user {}", username);
        Map<Currency, BigDecimal> currencies = userAccount.getCurrencies();

        if (currencies.get(from).compareTo(amount) < 0) {
            String message = username + " could not exchange " + amount + from + ", only " +
                    currencies.get(from) + from + " is available";
            logger.info("Error while exchanging: {}", message);
            throw new InsufficientAmountException(message);
        }

        BigDecimal exchangedAmount = amount.multiply(valueOf(to.getExchangeRate())).divide(valueOf(from.getExchangeRate()), 2, HALF_EVEN);
        currencies.put(to, exchangedAmount);

        BigDecimal newBalance = new BigDecimal(String.valueOf(currencies.get(from).subtract(amount)));
        currencies.put(from, newBalance);

        logger.info("User {} exchanged {} {} to {} {}", userAccount.getUsername(), amount, from.name(), exchangedAmount, to.name());
        return repository.save(userAccount);
    }

}
