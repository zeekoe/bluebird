package com.github.zeekoe.bluebird.influx;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import static com.github.zeekoe.bluebird.infrastructure.BluebirdProperties.property;
import static com.github.zeekoe.bluebird.infrastructure.BluebirdProperty.INFLUXDB_DATABASE;
import static com.github.zeekoe.bluebird.infrastructure.BluebirdProperty.INFLUXDB_PASSWORD;
import static com.github.zeekoe.bluebird.infrastructure.BluebirdProperty.INFLUXDB_URL;
import static com.github.zeekoe.bluebird.infrastructure.BluebirdProperty.INFLUXDB_USERNAME;

public class RealInfluxConnection implements InfluxConnection {
    private final InfluxDB influxDB;

    public RealInfluxConnection() {
        influxDB = InfluxDBFactory.connect(
                property(INFLUXDB_URL),
                property(INFLUXDB_USERNAME),
                property(INFLUXDB_PASSWORD));
    }

    public void writePoint(Point point) {
        influxDB.setDatabase(property(INFLUXDB_DATABASE));
        influxDB.write(point);
    }
}
