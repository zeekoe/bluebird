package com.github.zeekoe.bluebird.influx;

import com.github.zeekoe.bluebird.heatpump.model.HeatpumpLog;
import org.influxdb.dto.Point;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import static com.github.zeekoe.bluebird.infrastructure.BluebirdProperties.property;
import static com.github.zeekoe.bluebird.infrastructure.BluebirdProperty.INFLUXDB_MEASUREMENT;

public class PointMapper {
  @NotNull
  public static Point map(HeatpumpLog heatpumpLog) {
    return Point.measurement(property(INFLUXDB_MEASUREMENT))
        .time(heatpumpLog.getTimestamp().toEpochSecond(), TimeUnit.SECONDS)
        .addField("state", heatpumpLog.getState())
        .addField("t_1", heatpumpLog.getT1())
        .addField("t_2", heatpumpLog.getT2())
        .addField("fan_power", heatpumpLog.getFanPower())
        .addField("t_compressor_in", heatpumpLog.gettCompressorIn())
        .addField("t_compressor_in_transient", heatpumpLog.gettCompressorInTransient())
        .addField("t_compressor_out", heatpumpLog.gettCompressorOut())
        .addField("t_air_in", heatpumpLog.gettAirIn())
        .addField("t_air_out", heatpumpLog.gettAirOut())
        .addField("t_water_in", heatpumpLog.gettWaterIn())
        .addField("t_water_out", heatpumpLog.gettWaterOut())
        .addField("t_compressor_out_transient", heatpumpLog.getT_compressor_out_transient())
        .addField("p_compressor_in", heatpumpLog.getP_compressor_in())
        .addField("p_compressor_out", heatpumpLog.getP_compressor_out())
        .addField("rpm", heatpumpLog.getRpm())
        .addField("fan", heatpumpLog.getFan())
        .addField("t_inverter", heatpumpLog.getT_inverter())
        .addField("compressor_power_low_accuracy", heatpumpLog.getCompressor_power_low_accuracy())
        .addField("t_room", heatpumpLog.gettRoom())
        .addField("t_room_target", heatpumpLog.gettRoomTarget())
        .addField("t_thermostat_setpoint", heatpumpLog.gettThermostatSetpoint())
        .addField("cm_mass_power_in", heatpumpLog.getCmMassPowerIn())
        .addField("cm_mass_power_out", heatpumpLog.getCmMassPowerOut())
        .addField("t_water_house_in", heatpumpLog.gettWaterHouseIn())
        .addField("cm_mass_flow", heatpumpLog.getCm_mass_flow())
        .addField("ot_boiler_feed_temperature", heatpumpLog.getOt_boiler_feed_temperature())
        .addField("ot_boiler_return_temperature", heatpumpLog.getOt_boiler_return_temperature())
        .addField("error", heatpumpLog.getError())
        .build();
  }
}
