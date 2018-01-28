package com.crypto.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class HibernateUtils {

    /**
     * Logging
     */
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtils.class);

    /**
     * Factory for Hibernate sessions
     */
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Properties props = new Properties();
            props.load(HibernateUtils.class.getClassLoader().getResourceAsStream("hibernate.properties"));

            return new Configuration().mergeProperties(props).configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Close down hibernate connections
     */
    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}
