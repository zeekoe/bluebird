package com.github.zeekoe.bluebird.influx;

import org.influxdb.dto.Point;

public interface InfluxConnection {
  void writePoint(Point point);
}
