package com.github.zeekoe.bluebird.influx;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.zeekoe.bluebird.infrastructure.BluebirdProperties.property;
import static com.github.zeekoe.bluebird.infrastructure.BluebirdProperty.INFLUXDB_DATABASE;
import static com.github.zeekoe.bluebird.infrastructure.BluebirdProperty.INFLUXDB_MEASUREMENT;
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
    influxDB.setDatabase(property(INFLUXDB_DATABASE));
  }

  public void writePoint(Point point) {
    influxDB.write(point);
  }

  public List<ZonedDateTime> retrieveInfluxedTimesBetween(ZonedDateTime oldestLog, ZonedDateTime newestLog) {
    final String queryString = "SELECT \"t_room\" FROM \"" + property(INFLUXDB_MEASUREMENT) + "\" " +
        " WHERE time >= '" + oldestLog.format(DateTimeFormatter.ISO_ZONED_DATE_TIME) + "'" +
        " and time <= '" + newestLog.format(DateTimeFormatter.ISO_ZONED_DATE_TIME) + "';";
    final QueryResult result = influxDB.query(new Query(queryString));
    final List<QueryResult.Series> series1 = result.getResults().get(0).getSeries();

    return series1.get(0).getValues()
        .stream().map(s -> ZonedDateTime.parse(s.get(0).toString())
            .truncatedTo(ChronoUnit.SECONDS))
        .collect(Collectors.toList());
  }
}
