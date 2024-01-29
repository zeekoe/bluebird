package com.github.zeekoe.bluebird.infrastructure;


public enum BluebirdProperty {
  WEHEAT_USERNAME("bluebird.username"),
  WEHEAT_PASSWORD("bluebird.password"),
  WEHEAT_LOG_URL("bluebird.logurl"),

  INFLUXDB_URL("influxdb.url"),
  INFLUXDB_DATABASE("influxdb.database"),
  INFLUXDB_USERNAME("influxdb.username"),
  INFLUXDB_PASSWORD("influxdb.password"),
  INFLUXDB_MEASUREMENT("influxdb.bluebird.measurement"),

  GAPFILLER_WINDOW_MINUTES("gapfiller.window.minutes", "10");

  private final String key;
  private final String defaultValue;

  BluebirdProperty(String key) {
    this.key = key;
    this.defaultValue = "";
  }

  BluebirdProperty(String key, String defaultValue) {
    this.key = key;
    this.defaultValue = defaultValue;
  }

  public String getKey() {
    return key;
  }

  public String getDefaultValue() {
    return defaultValue;
  }
}
