package com.github.zeekoe.bluebird.infrastructure;


public enum BluebirdProperty {
    WEHEAT_USERNAME("bluebird.username"),
    WEHEAT_PASSWORD("bluebird.password"),
    WEHEAT_LOG_URL("bluebird.logurl"),

    INFLUXDB_URL("influxdb.url"),
    INFLUXDB_DATABASE("influxdb.database"),
    INFLUXDB_USERNAME("influxdb.username"),
    INFLUXDB_PASSWORD("influxdb.password"),
    INFLUXDB_MEASUREMENT("influxdb.bluebird.measurement");

    private final String key;

    BluebirdProperty(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
