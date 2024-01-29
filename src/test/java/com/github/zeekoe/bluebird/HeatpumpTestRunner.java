package com.github.zeekoe.bluebird;

import com.github.zeekoe.bluebird.heatpump.Heatpump;
import com.github.zeekoe.bluebird.influx.DummyInfluxConnection;

public class HeatpumpTestRunner {
    public static void main(String[] args) {
        final Heatpump heatpump = new Heatpump(new DummyInfluxConnection());
        heatpump.run();
//        final LocalDateTime localDate = Instant.ofEpochSecond(1706523458).atOffset(ZoneOffset.ofHours(1)).toLocalDateTime();
//        System.out.println(localDate);
    }
}
