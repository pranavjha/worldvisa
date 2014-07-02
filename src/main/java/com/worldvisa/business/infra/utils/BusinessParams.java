package com.worldvisa.business.infra.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.management.RuntimeErrorException;

/**
 * Defines BusinessParams
 */
public class BusinessParams {
    private static Properties property;
    static {
        BusinessParams.property = new Properties();
        final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("properties/applicationparams.properties");
        try {
            BusinessParams.property.load(inputStream);
        } catch (final IOException e) {
            throw new RuntimeErrorException(new Error("application params could not be loaded"));
        }
    }

    public static String get(final String key) {
        return BusinessParams.property.getProperty(key);
    }
}
