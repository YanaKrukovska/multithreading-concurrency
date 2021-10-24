package com.krukovska.multithreading.task5.service;

import com.krukovska.multithreading.task5.model.Currency;
import com.krukovska.multithreading.task5.model.UserAccount;

import java.math.BigDecimal;

public interface UserAccountService {

    UserAccount findByUsername(String username);

    UserAccount save(UserAccount account);

    UserAccount exchangeCurrency(String username, Currency from, Currency to, BigDecimal amount);
}
