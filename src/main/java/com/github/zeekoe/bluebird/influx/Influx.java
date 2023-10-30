package com.github.zeekoe.bluebird.influx;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Influx {

    private final String influxUrl;
    private final String databaseName;
    private final String username;
    private final String password;
    private final String measurement;

    public void writePoint(Point point) {
        // TODO keep InfluxDB instance
        InfluxDB influxDB = InfluxDBFactory.connect(influxUrl, username, password);
        influxDB.setDatabase(databaseName);
        influxDB.write(point);
    }

    public Influx() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("/etc/bluebird.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        influxUrl = properties.getProperty("influxdb.url");
        databaseName = properties.getProperty("influxdb.database");
        username = properties.getProperty("influxdb.username");
        password = properties.getProperty("influxdb.password");
        measurement = properties.getProperty("influxdb.bluebird.measurement");
    }

    public String getMeasurementIdentifier() {
        return measurement;
    }
}
