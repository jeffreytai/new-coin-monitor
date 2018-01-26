package com.crypto.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class WebUtils {

    /**
     * Logging
     */
    private static final Logger logger = LoggerFactory.getLogger(WebUtils.class);

    /**
     * User agent for web requests
     */
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36";

    /**
     * Number of times to retry connecting to a url
     */
    private static final Integer MAX_RETRY_COUNT = 3;

    /**
     * Empty constructor
     */
    public WebUtils() {}

    /**
     * Attempt connecting to a url MAX_RETRY_COUNT number of times before returning an error
     * @param requestUrl
     * @return
     */
    public static Document htmlDocument(final String requestUrl) {
        for (int retryCount = 0; retryCount < MAX_RETRY_COUNT; retryCount++) {
            try {
                Document doc = Jsoup.connect(requestUrl).userAgent(USER_AGENT).get();
                return doc;
            } catch (IOException ex) {
                // Do nothing, the for loop will handle it
            }
        }

        return null;
    }
}
