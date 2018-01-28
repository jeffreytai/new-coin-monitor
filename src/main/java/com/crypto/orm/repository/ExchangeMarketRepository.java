package com.crypto.orm.repository;

import com.crypto.orm.entity.ExchangeMarket;
import com.crypto.utils.DbUtils;

import java.util.HashMap;
import java.util.Map;

public class ExchangeMarketRepository {

    public static ExchangeMarket findByExchangeIdAndPair(Long exchangeId, String pair) {
        String query = "SELECT em FROM ExchangeMarket em WHERE em.exchangeId = :exchangeId AND em.pair = :pair";
        Map<Object, Object> bindedParameters = new HashMap<>();
        bindedParameters.put("exchangeId", exchangeId);
        bindedParameters.put("pair", pair);

        ExchangeMarket exchangeMarket = (ExchangeMarket) DbUtils.runSingularResultQuery(query, bindedParameters);
        return exchangeMarket;
    }
}
