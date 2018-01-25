package com.crypto.lookup;

import com.crypto.api.CoinMarketCap;
import com.crypto.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CoinDictionary {

    private static final Logger logger = LoggerFactory.getLogger(CoinDictionary.class);

    /**
     * Thread-safe implementation of Singleton instance
     */
    private static volatile CoinDictionary instance;

    /**
     * Dictionary of ticker to names
     */
    private static Map<String, String> dictionary;

    /**
     * Protected constructor, cannot be called externally
     */
    protected CoinDictionary() {
        if (instance != null) {
            throw new RuntimeException("Use getInstance() to get the singleton instance");
        }

        logger.info("Creating ticker map");

        CoinMarketCap tm = new CoinMarketCap();
        dictionary = tm.createTickerMapping();
    }

    /**
     * Get singleton instance of CoinDictionary
     * @return
     */
    public static CoinDictionary getInstance() {
        // If there is no existing instance, create a new one
        if (instance == null) {
            synchronized (CoinDictionary.class) {
                // Check instance again to confirm locking mechanism
                if (instance == null) {
                    instance = new CoinDictionary();
                }
            }
        }

        return instance;
    }

    /**
     * Given a ticker, return the coin name if exists
     * @param ticker
     * @return
     */
    public String getName(String ticker) {
        return dictionary.containsKey(ticker) ? dictionary.get(ticker) : StringUtils.EMPTY_STRING;
    }
}
