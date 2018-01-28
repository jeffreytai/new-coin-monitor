package com.crypto.orm.repository;

import com.crypto.orm.entity.Exchange;
import com.crypto.utils.DbUtils;

import java.util.HashMap;
import java.util.Map;

public class ExchangeRepository {

    /**
     * Returns the exchange entity by name
     * @param exchangeName
     * @return
     */
    public static Exchange findByName(String exchangeName) {
        String query = "SELECT e FROM Exchange e WHERE e.name = :exchangeName";
        Map<Object, Object> bindedParameters = new HashMap<>();
        bindedParameters.put("exchangeName", exchangeName);

        Exchange exchange = (Exchange) DbUtils.runSingularResultQuery(query, bindedParameters);
        return exchange;
    }
}
