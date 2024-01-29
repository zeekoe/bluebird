package com.github.zeekoe.bluebird.heatpump;

import com.github.zeekoe.bluebird.heatpump.model.HeatpumpLog;
import com.github.zeekoe.bluebird.influx.PointMapper;
import com.github.zeekoe.bluebird.influx.RealInfluxConnection;
import com.github.zeekoe.bluebird.infrastructure.BluebirdProperties;
import com.github.zeekoe.bluebird.infrastructure.BluebirdProperty;
import org.influxdb.dto.Point;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Gapfiller {
  private ZonedDateTime gapStartTime;
  private ZonedDateTime nextFillTime;
  private int timeWindow;

  public Gapfiller() {
    this.gapStartTime = ZonedDateTime.now().minusHours(2);
    System.out.println("Gapfiller fresh start.");
    nextFillTime = ZonedDateTime.now().plusSeconds(10);
    printGapfillerWindow();
    setTimeWindow();

  }

  private void setTimeWindow() {
    this.timeWindow = 10; // minimum of 10 minutes to be gentle for the server
    try {
      String timeWindowString = BluebirdProperties.property(BluebirdProperty.GAPFILLER_WINDOW_MINUTES);
      int timeWindowInt = Integer.parseInt(timeWindowString);
      if (timeWindowInt > 10) {
        this.timeWindow = timeWindowInt;
      }
    } catch (Exception ignored) {
    }
  }

  public void checkAndRun() {
    try {
      if (ZonedDateTime.now().isAfter(nextFillTime)) {
        HeatpumpLog[] heatpumpLogs = new Heatpump().doHeatpumpGapRequest(gapStartTime);
        fillTheGaps(heatpumpLogs);
        gapStartTime = nextFillTime;
        nextFillTime = ZonedDateTime.now()
            .plusMinutes(timeWindow)
            .plusSeconds(new Random().nextInt(20)); // wait a little after the initial startup server load
        printGapfillerWindow();
      }
    } catch (Exception e) {
      System.out.println("Gapfiller failed: " + e.getMessage());
    }
  }

  private void printGapfillerWindow() {
    System.out.println("Gapfiller next window: " + gapStartTime + " - " + nextFillTime);
  }

  private void fillTheGaps(HeatpumpLog[] heatpumplogs) {
    if (heatpumplogs.length < 2) {
      System.out.println("Please call this method for a bigger period");
      return;
    }
    List<Point> pointsTodo = new ArrayList<>();
    int doneCount = 0;

    ZonedDateTime oldestLog = Arrays.stream(heatpumplogs).min(Comparator.comparing(HeatpumpLog::getTimestamp))
        .orElseThrow().getTimestamp().truncatedTo(ChronoUnit.SECONDS);
    ZonedDateTime newestLog = Arrays.stream(heatpumplogs).max(Comparator.comparing(HeatpumpLog::getTimestamp))
        .orElseThrow().getTimestamp().truncatedTo(ChronoUnit.SECONDS);

    System.out.println("Filling gaps from " + oldestLog + " to " + newestLog);

    RealInfluxConnection influxConnection = new RealInfluxConnection();
    List<ZonedDateTime> influxedTimes = influxConnection.retrieveInfluxedTimesBetween(oldestLog, newestLog);

    for (HeatpumpLog heatpumplog : heatpumplogs) {
      if (!influxedTimes.contains(heatpumplog.getTimestamp().truncatedTo(ChronoUnit.SECONDS))) {
        Point point = PointMapper.map(heatpumplog);
        pointsTodo.add(point);

      } else {
        doneCount++;
      }
    }
    System.out.println("\nWill fill gaps: " + pointsTodo.size() + ", already logged: " + doneCount);
    if (doneCount > 4) { // detect errors
      for (Point point : pointsTodo) {
        influxConnection.writePoint(point);
      }
    }
    System.out.println("Gapfiller done!");
  }
}
