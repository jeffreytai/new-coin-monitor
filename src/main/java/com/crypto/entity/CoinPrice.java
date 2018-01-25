package com.crypto.entity;

public class CoinPrice {

    /**
     * Name of coin
     */
    private String name;

    /**
     * Ticker or symbol
     */
    private String ticker;

    /**
     * Price in US dollars
     */
    private Double usdPrice;

    /**
     * Price change as a percentage in the past 24 hours
     */
    private Double percentageChange24h;


    /***************
     * Constructor
     **************/
    public CoinPrice(String name, String ticker, Double usdPrice, Double percentageChange24h) {
        this.name = name;
        this.ticker = ticker;
        this.usdPrice = usdPrice;
        this.percentageChange24h = percentageChange24h;
    }

}
