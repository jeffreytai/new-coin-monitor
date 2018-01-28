package com.crypto.coinmarketcap;

import com.crypto.utils.ApiUtils;
import com.crypto.utils.StreamUtils;
import com.crypto.utils.StringUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PriceCoinMonitor {

    /**
     * Logging
     */
    private static final Logger logger = LoggerFactory.getLogger(PriceCoinMonitor.class);

    /**
     * Url to retrieve all tickers from CoinMarketCap
     */
    private static final String ALL_TICKER_URL = "https://api.coinmarketcap.com/v1/ticker/";

    /**
     * Constructor
     */
    public PriceCoinMonitor() {}

    /**
     * Pull all tickers from CoinMarketCap without limits
     * @return
     */
    public JsonArray getAllTickers() {
        // Retrieve all available tickers
        JsonArray allTickers = ApiUtils.getJsonArray(ALL_TICKER_URL, "limit", "0");

        return allTickers;
    }

    /**
     * Create a map of tickers to coin names
     * @return
     */
    public Map<String, String> createTickerMapping() {
        Map<String, String> tickerMap = new HashMap<>();

        // Get all tickers
        JsonArray allTickers = getAllTickers();

        // Create map of ticker to coin name
        for (JsonElement ticker : allTickers) {
            JsonObject element = ticker.getAsJsonObject();
            tickerMap.put(element.get("symbol").getAsString(), element.get("name").getAsString());
        }

        return tickerMap;
    }

    /**
     * Get the information pertaining to a list of tickers.
     * @param subscribedTickers is a map of each person to the list of tickers they're subscribed to
     * @return
     */
    public String filterInformation(Map<String, List<String>> subscribedTickers) {
        JsonArray allTickers = getAllTickers();

        Set<JsonObject> filteredTickers = StreamUtils.arrayToStream(allTickers)
                .map(JsonObject.class::cast)
                .filter(j -> subscribedTickers.values()
                        .stream()
                        .flatMap(List::stream)
                        .anyMatch(t ->
                                StringUtils.caseInsensitiveEquals(t, j.get("symbol").getAsString())))
                .collect(Collectors.toSet());


        // TODO: Filter tickers by subscriber
        return null;

    }
}
