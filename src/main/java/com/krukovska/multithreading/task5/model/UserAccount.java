package com.krukovska.multithreading.task5.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class UserAccount {

    private String username;
    private Map<Currency, BigDecimal> currencies = new HashMap<>();

    public UserAccount(String username, Map<Currency, BigDecimal> currencies) {
        this.username = username;
        this.currencies = currencies;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<Currency, BigDecimal> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<Currency, BigDecimal> currencies) {
        this.currencies = currencies;
    }

    @Override
    public String toString() {
        return username + ": " + currencies;
    }
}
