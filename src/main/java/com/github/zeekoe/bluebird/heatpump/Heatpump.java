package com.github.zeekoe.bluebird.heatpump;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.zeekoe.bluebird.heatpump.model.HeatpumpLog;
import com.github.zeekoe.bluebird.influx.InfluxConnection;
import com.github.zeekoe.bluebird.influx.PointMapper;
import com.github.zeekoe.bluebird.influx.RealInfluxConnection;
import com.github.zeekoe.bluebird.infrastructure.MyHttpClient;
import org.influxdb.dto.Point;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.github.zeekoe.bluebird.infrastructure.BluebirdProperties.property;
import static com.github.zeekoe.bluebird.infrastructure.BluebirdProperty.WEHEAT_LOG_URL;

public class Heatpump implements Runnable {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    private static final MyHttpClient httpClient = new MyHttpClient();

    private final Auth auth;
    private final InfluxConnection influxConnection;

    @SuppressWarnings("unused") // instantiated by Retryer
    public Heatpump() {
        auth = new Auth();
        influxConnection = new RealInfluxConnection();
    }

    public Heatpump(InfluxConnection influxConnection) {
        auth = new Auth();
        this.influxConnection = influxConnection;
    }

    @Override
    public void run() {
        try {
            final HeatpumpLog heatpumpLog = doHeatpumpRequest();
            System.out.print(heatpumpLog.gettRoom() + " ");
            influx(heatpumpLog);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void influx(HeatpumpLog heatpumpLog) {
        final Point point = PointMapper.map(heatpumpLog);
        influxConnection.writePoint(point);
    }

    private HeatpumpLog doHeatpumpRequest() throws IOException, InterruptedException {
        final String responseBody = httpClient.get(property(WEHEAT_LOG_URL), auth.getToken());
        return OBJECT_MAPPER.readValue(responseBody, HeatpumpLog.class);
    }

    public HeatpumpLog[] doHeatpumpGapRequest(int minutes) throws IOException, InterruptedException {
        final ZonedDateTime now = ZonedDateTime.now();
        final String from = formatDateTime(now.minusMinutes(minutes));
        final String to = formatDateTime(now);

        String url = property(WEHEAT_LOG_URL).replace("/latest","") + "/raw?startTime=" + from  + "&endTime=" + to;
        return OBJECT_MAPPER.readValue(httpClient.get(url, auth.getToken()), HeatpumpLog[].class);
    }

    public String formatDateTime(ZonedDateTime time) {
        // TODO There must be a better way...
        final ZonedDateTime utcTime = time.withZoneSameInstant(ZoneId.of("UTC"));
        return utcTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "T"
        + utcTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "Z";
    }
}
