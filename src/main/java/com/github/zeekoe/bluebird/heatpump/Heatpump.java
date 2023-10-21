package com.github.zeekoe.bluebird.heatpump;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.zeekoe.bluebird.heatpump.model.HeatpumpLog;
import com.github.zeekoe.bluebird.influx.Influx;
import org.influxdb.dto.Point;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

public class Heatpump implements Runnable {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private final Auth auth;

    public Heatpump() {
        auth = new Auth();
    }

    @Override
    public void run() {
        try {
            final String responseBody = doHeatpumpRequest();

            final String heatpumpLogString = responseBody.replace("[", "").replace("]", "");

            ObjectMapper objectMapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule());
            final HeatpumpLog heatpumpLog = objectMapper.readValue(heatpumpLogString, HeatpumpLog.class);
            System.out.print(heatpumpLog.getT_room() + " ");
            influx(heatpumpLog);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void influx(HeatpumpLog heatpumpLog) {
        final Influx influx = new Influx(); // TODO reuse Influx instance
        final Point point = Point.measurement(influx.getMeasurementIdentifier())
                .time(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond(), TimeUnit.SECONDS)
                .addField("state", heatpumpLog.getState())
                .addField("t_1", heatpumpLog.getT_1())
                .addField("t_2", heatpumpLog.getT_2())
                .addField("fan_power", heatpumpLog.getFan_power())
                .addField("t_compressor_in", heatpumpLog.getT_compressor_in())
                .addField("t_compressor_in_transient", heatpumpLog.getT_compressor_in_transient())
                .addField("t_compressor_out", heatpumpLog.getT_compressor_out())
                .addField("t_air_in", heatpumpLog.getT_air_in())
                .addField("t_air_out", heatpumpLog.getT_air_out())
                .addField("t_water_in", heatpumpLog.getT_water_in())
                .addField("t_water_out", heatpumpLog.getT_water_out())
                .addField("t_compressor_out_transient", heatpumpLog.getT_compressor_out_transient())
                .addField("p_compressor_in", heatpumpLog.getP_compressor_in())
                .addField("p_compressor_out", heatpumpLog.getP_compressor_out())
                .addField("rpm", heatpumpLog.getRpm())
                .addField("fan", heatpumpLog.getFan())
                .addField("t_inverter", heatpumpLog.getT_inverter())
                .addField("compressor_power_low_accuracy", heatpumpLog.getCompressor_power_low_accuracy())
                .addField("t_room", heatpumpLog.getT_room())
                .addField("t_room_target", heatpumpLog.getT_room_target())
                .addField("cm_mass_power_in", heatpumpLog.getCm_mass_power_in())
                .addField("cm_mass_power_out", heatpumpLog.getCm_mass_power_out())
                .addField("t_water_house_in", heatpumpLog.getT_water_house_in())
                .addField("cm_mass_flow", heatpumpLog.getCm_mass_flow())
                .addField("ot_boiler_feed_temperature", heatpumpLog.getOt_boiler_feed_temperature())
                .addField("ot_boiler_return_temperature", heatpumpLog.getOt_boiler_return_temperature())
                .build();
        influx.writePoint(point);
    }

    private String doHeatpumpRequest() throws IOException, InterruptedException {
        final String url = auth.getLogurl();
        final String apiKey = auth.getApikey();
        final String authorization = "Bearer " + auth.getToken();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .setHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/115.0") // add request header
                .setHeader("apikey", apiKey)
                .setHeader("authorization", authorization)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Incorrect response: " + response);
        }

        return response.body();
    }
}
