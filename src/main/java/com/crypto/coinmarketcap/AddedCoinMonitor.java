package com.crypto.coinmarketcap;

import com.crypto.exception.InvalidExchangeException;
import com.crypto.orm.entity.Exchange;
import com.crypto.orm.entity.ExchangeMarket;
import com.crypto.orm.repository.ExchangeMarketRepository;
import com.crypto.orm.repository.ExchangeRepository;
import com.crypto.slack.SlackWebhook;
import com.crypto.utils.DbUtils;
import com.crypto.utils.StringUtils;
import com.crypto.utils.WebUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class AddedCoinMonitor {

    /**
     * Logging
     */
    private static final Logger logger = LoggerFactory.getLogger(AddedCoinMonitor.class);

    /**
     * Base url for specific exchange
     */
    private static final String BASE_EXCHANGE_URL = "https://coinmarketcap.com/exchanges/";

    /**
     * Url to retrieve all exchanges and the respective listed coins
     */
    private static final String ALL_EXCHANGES_URL = "https://coinmarketcap.com/exchanges/volume/24-hour/all/";


    /**
     * Constructor
     */
    public AddedCoinMonitor() {}


    /**
     * Load the database with the current snapshot of all exchanges and supported cryptocurrencies
     * Assumes that the database is empty prior to execution
     */
    public void initializeSnapshot() {
        Elements exchangeNames = getAllExchangeNames();

        for (Element element : exchangeNames) {
            try {
                String exchangeName = element.text();
                Document exchangeDoc = getExchangeHtmlDoc(exchangeName);

                logger.info("Saving {} entity", exchangeName);
                Exchange exchange = new Exchange(exchangeName, exchangeDoc.location());
                DbUtils.saveEntity(exchange);

                logger.info("Extracting supported coins for {}", exchangeName);
                Elements activeMarkets = exchangeDoc.select("#exchange-markets tr .market-name");
                List<ExchangeMarket> supportedMarkets = new ArrayList<>();

                for (Element market : activeMarkets) {
                    String coin = market.text();
                    String pair = market.parent().nextElementSibling().text();
                    logger.info("Found {} for {}", pair, exchangeName);

                    ExchangeMarket exchangeMarket = new ExchangeMarket(exchange.getId(), coin, pair);
                    supportedMarkets.add(exchangeMarket);
                }

                DbUtils.saveEntities(supportedMarkets);
            } catch (NullPointerException | InvalidExchangeException ex) {
                continue;
            }

        }
    }

    /**
     * Check for newly added exchanges or coins
     */
    public void currentExchangeSnapshot() {
        Elements exchangeNames = getAllExchangeNames();

        Map<String, List<ExchangeMarket>> newTradingPairs = new HashMap<>();
        for (Element element : exchangeNames) {
            try {
                // Retrieve the active markets for each exchange
                String exchangeName = element.text();
                Document exchangeDoc = getExchangeHtmlDoc(exchangeName);
                Elements activeMarkets = exchangeDoc.select("#exchange-markets tr .market-name");
                logger.info("Checking active markets for {}", exchangeName);

                // Save entity if the exchange is new
                Exchange existingExchange = ExchangeRepository.findByName(exchangeName);
                Exchange newExchange = null;
                if (existingExchange == null) {
                    newExchange = new Exchange(exchangeName, exchangeDoc.location());
                    DbUtils.saveEntity(newExchange);
                }

                List<ExchangeMarket> newMarkets = new ArrayList<>();
                for (Element market : activeMarkets) {
                    String coin = market.text();
                    String pair = market.parent().nextElementSibling().text();

                    // New markets will be created for new exchanges
                    if (newExchange != null) {
                        ExchangeMarket newMarket = new ExchangeMarket(newExchange.getId(), coin, pair);
                        newMarkets.add(newMarket);
                    }
                    else {
                        // Check if the market already exists for the associated exchange
                        ExchangeMarket existingMarket = ExchangeMarketRepository.findByExchangeIdAndPair(existingExchange.getId(), pair);
                        if (existingMarket == null) {
                            ExchangeMarket newMarket = new ExchangeMarket(existingExchange.getId(), coin, pair);
                            newMarkets.add(newMarket);
                        }
                    }
                }

                // Save all new markets
                if (newMarkets.size() > 0) {
                    newTradingPairs.put(exchangeName, newMarkets);
                    DbUtils.saveEntities(newMarkets);
                    logger.info("{} new markets found for {}", newMarkets.size(), exchangeName);
                }

            } catch (NullPointerException | InvalidExchangeException ex) {
                continue;
            }
        }

        sendSlackAlert(newTradingPairs);
    }

    /**
     * Sends a formatted message to Slack for new markets added to exchanges
     * Message format: "HitBTC added new markets: ['ADX/BTC', 'BQX/USD', 'PLR/USD', 'PRE/BTC']"
     * @param newTradingPairs
     */
    private void sendSlackAlert(Map<String, List<ExchangeMarket>> newTradingPairs) {
        if (newTradingPairs.size() > 0) {
            SlackWebhook slack = new SlackWebhook("new-markets-alert");

            Supplier<TreeSet<String>> orderedPairs = () -> new TreeSet<>();

            for (Map.Entry<String, List<ExchangeMarket>> newTradingPair : newTradingPairs.entrySet()) {
                String exchangeName = newTradingPair.getKey();
                String tradingPairs = String.join(", ", newTradingPair.getValue()
                        .stream()
                        .map(em -> "'" + em.getPair() + "'")
                        .collect(Collectors.toCollection(orderedPairs)));

                String message = String.format("%s added new markets: [%s]", exchangeName, tradingPairs);

                slack.sendMessage(message);
            }

            slack.shutdown();
        }
    }

    /**
     * Retrieves the names of all exchanges
     * @return
     */
    private Elements getAllExchangeNames() {
        logger.info("Extracting all exchanges");

        Document doc = WebUtils.htmlDocument(ALL_EXCHANGES_URL);
        Elements exchangeNames = doc.select(".table-responsive tr .volume-header a");

        return exchangeNames;
    }

    /**
     * Retrieves the Jsoup document for the exchange page
     * @param exchangeName
     * @return
     * @throws InvalidExchangeException
     */
    private Document getExchangeHtmlDoc(String exchangeName) throws InvalidExchangeException {
        String exchangeUrl = BASE_EXCHANGE_URL + StringUtils.urlFormattedString(exchangeName);

        logger.info("Connecting to {}", exchangeUrl);
        Document exchangeDoc = WebUtils.htmlDocument(exchangeUrl);

        if (exchangeDoc == null) {
            String errorMessage = String.format("Unable to connect to %s", exchangeUrl);
            logger.error(errorMessage);
            throw new InvalidExchangeException(errorMessage);
        }

        return exchangeDoc;
    }
}
