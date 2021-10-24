package com.krukovska.multithreading.task5.model;

public enum Currency {

    UAH(1.0),
    USD(0.038),
    EUR(0.033);

    private final double exchangeRate;

    Currency(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }
}