package com.github.zeekoe.bluebird.heatpump.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HeatpumpLog {
    private LocalDateTime timestamp;
    private long unix_time_mcu;
    private int state;
    private int error;
    private double t1;
    private double t2;
    private double fanPower;
    private double tCompressorIn;
    private double tCompressorInTransient;
    private double tCompressorOut;
    private double tAirIn;
    private double tAirOut;
    private double tWaterIn;
    private double tWaterOut;
    private double t_compressor_out_transient;
    private double p_compressor_in;
    private double p_compressor_out;
    private double rpm;
    private double fan;
    private double t_inverter;
    private double compressor_power_low_accuracy;
    private double tRoom;
    private double tRoomTarget;
    private double tThermostatSetpoint;
    private double cmMassPowerIn;
    private double cmMassPowerOut;
    private double tWaterHouseIn;
    private double cm_mass_flow;
    private double ot_boiler_feed_temperature;
    private double ot_boiler_return_temperature;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public long getUnix_time_mcu() {
        return unix_time_mcu;
    }

    public void setUnix_time_mcu(long unix_time_mcu) {
        this.unix_time_mcu = unix_time_mcu;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public double getT1() {
        return t1;
    }

    public void setT1(double t1) {
        this.t1 = t1;
    }

    public double getT2() {
        return t2;
    }

    public void setT2(double t2) {
        this.t2 = t2;
    }

    public double getFanPower() {
        return fanPower;
    }

    public void setFanPower(double fanPower) {
        this.fanPower = fanPower;
    }

    public double gettCompressorIn() {
        return tCompressorIn;
    }

    public void settCompressorIn(double tCompressorIn) {
        this.tCompressorIn = tCompressorIn;
    }

    public double gettCompressorInTransient() {
        return tCompressorInTransient;
    }

    public void settCompressorInTransient(double tCompressorInTransient) {
        this.tCompressorInTransient = tCompressorInTransient;
    }

    public double gettCompressorOut() {
        return tCompressorOut;
    }

    public void settCompressorOut(double tCompressorOut) {
        this.tCompressorOut = tCompressorOut;
    }

    public double gettAirIn() {
        return tAirIn;
    }

    public void settAirIn(double tAirIn) {
        this.tAirIn = tAirIn;
    }

    public double gettAirOut() {
        return tAirOut;
    }

    public void settAirOut(double tAirOut) {
        this.tAirOut = tAirOut;
    }

    public double gettWaterIn() {
        return tWaterIn;
    }

    public void settWaterIn(double tWaterIn) {
        this.tWaterIn = tWaterIn;
    }

    public double gettWaterOut() {
        return tWaterOut;
    }

    public void settWaterOut(double tWaterOut) {
        this.tWaterOut = tWaterOut;
    }

    public double getT_compressor_out_transient() {
        return t_compressor_out_transient;
    }

    public void setT_compressor_out_transient(double t_compressor_out_transient) {
        this.t_compressor_out_transient = t_compressor_out_transient;
    }

    public double getP_compressor_in() {
        return p_compressor_in;
    }

    public void setP_compressor_in(double p_compressor_in) {
        this.p_compressor_in = p_compressor_in;
    }

    public double getP_compressor_out() {
        return p_compressor_out;
    }

    public void setP_compressor_out(double p_compressor_out) {
        this.p_compressor_out = p_compressor_out;
    }

    public double getRpm() {
        return rpm;
    }

    public void setRpm(double rpm) {
        this.rpm = rpm;
    }

    public double getFan() {
        return fan;
    }

    public void setFan(double fan) {
        this.fan = fan;
    }

    public double getT_inverter() {
        return t_inverter;
    }

    public void setT_inverter(double t_inverter) {
        this.t_inverter = t_inverter;
    }

    public double getCompressor_power_low_accuracy() {
        return compressor_power_low_accuracy;
    }

    public void setCompressor_power_low_accuracy(double compressor_power_low_accuracy) {
        this.compressor_power_low_accuracy = compressor_power_low_accuracy;
    }

    public double gettRoom() {
        return tRoom;
    }

    public void settRoom(double tRoom) {
        this.tRoom = tRoom;
    }

    public double gettRoomTarget() {
        return tRoomTarget;
    }

    public void settRoomTarget(double tRoomTarget) {
        this.tRoomTarget = tRoomTarget;
    }

    public double gettThermostatSetpoint() {
        return tThermostatSetpoint;
    }

    public void settThermostatSetpoint(double tThermostatSetpoint) {
        this.tThermostatSetpoint = tThermostatSetpoint;
    }

    public double getCmMassPowerIn() {
        return cmMassPowerIn;
    }

    public void setCmMassPowerIn(double cmMassPowerIn) {
        this.cmMassPowerIn = cmMassPowerIn;
    }

    public double getCmMassPowerOut() {
        return cmMassPowerOut;
    }

    public void setCmMassPowerOut(double cmMassPowerOut) {
        this.cmMassPowerOut = cmMassPowerOut;
    }

    public double gettWaterHouseIn() {
        return tWaterHouseIn;
    }

    public void settWaterHouseIn(double tWaterHouseIn) {
        this.tWaterHouseIn = tWaterHouseIn;
    }

    public double getCm_mass_flow() {
        return cm_mass_flow;
    }

    public void setCm_mass_flow(double cm_mass_flow) {
        this.cm_mass_flow = cm_mass_flow;
    }

    public double getOt_boiler_feed_temperature() {
        return ot_boiler_feed_temperature;
    }

    public void setOt_boiler_feed_temperature(double ot_boiler_feed_temperature) {
        this.ot_boiler_feed_temperature = ot_boiler_feed_temperature;
    }

    public double getOt_boiler_return_temperature() {
        return ot_boiler_return_temperature;
    }

    public void setOt_boiler_return_temperature(double ot_boiler_return_temperature) {
        this.ot_boiler_return_temperature = ot_boiler_return_temperature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeatpumpLog that = (HeatpumpLog) o;
        return state == that.state && error == that.error && Double.compare(t1, that.t1) == 0 && Double.compare(t2, that.t2) == 0 && Double.compare(fanPower, that.fanPower) == 0 && Double.compare(tCompressorIn, that.tCompressorIn) == 0 && Double.compare(tCompressorInTransient, that.tCompressorInTransient) == 0 && Double.compare(tCompressorOut, that.tCompressorOut) == 0 && Double.compare(tAirIn, that.tAirIn) == 0 && Double.compare(tAirOut, that.tAirOut) == 0 && Double.compare(tWaterIn, that.tWaterIn) == 0 && Double.compare(tWaterOut, that.tWaterOut) == 0 && Double.compare(t_compressor_out_transient, that.t_compressor_out_transient) == 0 && Double.compare(p_compressor_in, that.p_compressor_in) == 0 && Double.compare(p_compressor_out, that.p_compressor_out) == 0 && Double.compare(rpm, that.rpm) == 0 && Double.compare(fan, that.fan) == 0 && Double.compare(t_inverter, that.t_inverter) == 0 && Double.compare(compressor_power_low_accuracy, that.compressor_power_low_accuracy) == 0 && Double.compare(tRoom, that.tRoom) == 0 && Double.compare(tRoomTarget, that.tRoomTarget) == 0 && Double.compare(cmMassPowerIn, that.cmMassPowerIn) == 0 && Double.compare(cmMassPowerOut, that.cmMassPowerOut) == 0 && Double.compare(tWaterHouseIn, that.tWaterHouseIn) == 0 && Double.compare(cm_mass_flow, that.cm_mass_flow) == 0 && Double.compare(ot_boiler_feed_temperature, that.ot_boiler_feed_temperature) == 0 && Double.compare(ot_boiler_return_temperature, that.ot_boiler_return_temperature) == 0 && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, state, error, t1, t2, fanPower, tCompressorIn, tCompressorInTransient, tCompressorOut, tAirIn, tAirOut, tWaterIn, tWaterOut, t_compressor_out_transient, p_compressor_in, p_compressor_out, rpm, fan, t_inverter, compressor_power_low_accuracy, tRoom, tRoomTarget, cmMassPowerIn, cmMassPowerOut, tWaterHouseIn, cm_mass_flow, ot_boiler_feed_temperature, ot_boiler_return_temperature);
    }

    @Override
    public String toString() {
        return "HeatpumpLog{" +
                "timestamp=" + timestamp +
                ", state=" + state +
                ", error=" + error +
                ", t_1=" + t1 +
                ", t_2=" + t2 +
                ", fan_power=" + fanPower +
                ", t_compressor_in=" + tCompressorIn +
                ", t_compressor_in_transient=" + tCompressorInTransient +
                ", t_compressor_out=" + tCompressorOut +
                ", t_air_in=" + tAirIn +
                ", t_air_out=" + tAirOut +
                ", t_water_in=" + tWaterIn +
                ", t_water_out=" + tWaterOut +
                ", t_compressor_out_transient=" + t_compressor_out_transient +
                ", p_compressor_in=" + p_compressor_in +
                ", p_compressor_out=" + p_compressor_out +
                ", rpm=" + rpm +
                ", fan=" + fan +
                ", t_inverter=" + t_inverter +
                ", compressor_power_low_accuracy=" + compressor_power_low_accuracy +
                ", t_room=" + tRoom +
                ", t_room_target=" + tRoomTarget +
                ", cm_mass_power_in=" + cmMassPowerIn +
                ", cm_mass_power_out=" + cmMassPowerOut +
                ", t_water_house_in=" + tWaterHouseIn +
                ", cm_mass_flow=" + cm_mass_flow +
                ", ot_boiler_feed_temperature=" + ot_boiler_feed_temperature +
                ", ot_boiler_return_temperature=" + ot_boiler_return_temperature +
                '}';
    }
}
