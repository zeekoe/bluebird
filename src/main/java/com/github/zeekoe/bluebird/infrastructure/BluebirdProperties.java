package com.github.zeekoe.bluebird.infrastructure;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BluebirdProperties {
    private static final Properties properties;

    static {
        final String configFile = "/etc/bluebird.config";
        properties = new Properties();
        try {
            properties.load(new FileInputStream(configFile));
        } catch (IOException e) {
            throw new RuntimeException("Error in loading properties file: " + configFile, e);
        }
    }

    public static String property(BluebirdProperty property) {
        return properties.getProperty(property.getKey());
    }
}
