package com.github.zeekoe.bluebird.heatpump;

import com.github.zeekoe.bluebird.heatpump.model.HeatpumpLog;
import com.github.zeekoe.bluebird.influx.PointMapper;
import com.github.zeekoe.bluebird.influx.RealInfluxConnection;
import org.influxdb.dto.Point;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Gapfiller {
    public static void main(String[] args) throws IOException, InterruptedException {
        final HeatpumpLog[] heatpumpLogs = new Heatpump().doHeatpumpGapRequest(2*60);
        fillTheGaps(heatpumpLogs);
    }
    private static void fillTheGaps(HeatpumpLog[] heatpumplogs) {
        if (heatpumplogs.length < 2) {
            System.out.println("Please call this method for a bigger period");
            return;
        }
        List<Point> pointsTodo = new ArrayList<>();
        int doneCount = 0;

        final ZonedDateTime oldestLog = Arrays.stream(heatpumplogs).min(Comparator.comparing(HeatpumpLog::getTimestamp))
                .orElseThrow().getTimestamp().truncatedTo(ChronoUnit.SECONDS);
        final ZonedDateTime newestLog = Arrays.stream(heatpumplogs).max(Comparator.comparing(HeatpumpLog::getTimestamp))
                .orElseThrow().getTimestamp().truncatedTo(ChronoUnit.SECONDS);

        final RealInfluxConnection influxConnection = new RealInfluxConnection();

        final List<ZonedDateTime> influxedTimes = influxConnection.retrieveInfluxedTimesBetween(oldestLog, newestLog);

        for (HeatpumpLog heatpumplog : heatpumplogs) {
            final boolean alreadyInfluxed = influxedTimes.contains(heatpumplog.getTimestamp().truncatedTo(ChronoUnit.SECONDS));
            System.out.println(heatpumplog.getTimestamp() + " " + (alreadyInfluxed ? "Y" : "N"));
            if (!alreadyInfluxed) {
                final Point point = PointMapper.map(heatpumplog);
                pointsTodo.add(point);

            } else {
                doneCount++;
            }
        }
        System.out.println("Todo: " + pointsTodo.size() + ", done: " + doneCount);
        if (doneCount > 4) { // detect errors
            for (Point point : pointsTodo) {
                influxConnection.writePoint(point);
            }
        }
        System.out.println("Done!");
    }
}
