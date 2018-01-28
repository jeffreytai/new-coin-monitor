package com.crypto;

import com.crypto.coinmarketcap.AddedCoinMonitor;
import com.crypto.utils.HibernateUtils;

public class Application {

    public static void main(String[] args) {
//        CoinDictionary dictionary = CoinDictionary.getInstance();

        AddedCoinMonitor cmc = new AddedCoinMonitor();
        // Load the initial snapshot
        // cmc.initializeSnapshot();

        // Examine any newly added markets or exchanges
         cmc.currentExchangeSnapshot();

        // Clean up Hibernate connections
        HibernateUtils.shutdown();
    }
}