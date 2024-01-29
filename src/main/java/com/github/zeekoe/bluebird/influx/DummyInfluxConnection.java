package com.github.zeekoe.bluebird.influx;

import org.influxdb.dto.Point;

public class DummyInfluxConnection implements InfluxConnection {
  @Override
  public void writePoint(Point point) {
    System.out.println(point);
  }
}
