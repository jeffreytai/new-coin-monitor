package com.crypto;

import com.crypto.api.CoinMarketCap;
import com.crypto.lookup.CoinDictionary;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
        CoinDictionary dictionary = CoinDictionary.getInstance();

        CoinMarketCap cmc = new CoinMarketCap();
        cmc.saveAllExchangeCoins();

//        Map<String, List<String>> subscribedTickers = new HashMap<>();
//        subscribedTickers.put("Jeffrey", Arrays.asList("BTC", "ETH"));
//
//        cmc.filterInformation(subscribedTickers);
    }
}