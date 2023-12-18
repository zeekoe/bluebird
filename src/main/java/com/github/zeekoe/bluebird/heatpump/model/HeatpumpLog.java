package com.github.zeekoe.bluebird.heatpump.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HeatpumpLog {
    private LocalDateTime timestamp;
    private long unix_time_mcu;
    private int state;
    private double t_1;
    private double t_2;
    private double fan_power;
    private double t_compressor_in;
    private double t_compressor_in_transient;
    private double t_compressor_out;
    private double t_air_in;
    private double t_air_out;
    private double t_water_in;
    private double t_water_out;
    private double t_compressor_out_transient;
    private double p_compressor_in;
    private double p_compressor_out;
    private double rpm;
    private double fan;
    private double t_inverter;
    private double compressor_power_low_accuracy;
    private double t_room;
    private double t_room_target;
    private double t_thermostat_setpoint;
    private double cm_mass_power_in;
    private double cm_mass_power_out;
    private double t_water_house_in;
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

    public double getT_1() {
        return t_1;
    }

    public void setT_1(double t_1) {
        this.t_1 = t_1;
    }

    public double getT_2() {
        return t_2;
    }

    public void setT_2(double t_2) {
        this.t_2 = t_2;
    }

    public double getFan_power() {
        return fan_power;
    }

    public void setFan_power(double fan_power) {
        this.fan_power = fan_power;
    }

    public double getT_compressor_in() {
        return t_compressor_in;
    }

    public void setT_compressor_in(double t_compressor_in) {
        this.t_compressor_in = t_compressor_in;
    }

    public double getT_compressor_in_transient() {
        return t_compressor_in_transient;
    }

    public void setT_compressor_in_transient(double t_compressor_in_transient) {
        this.t_compressor_in_transient = t_compressor_in_transient;
    }

    public double getT_compressor_out() {
        return t_compressor_out;
    }

    public void setT_compressor_out(double t_compressor_out) {
        this.t_compressor_out = t_compressor_out;
    }

    public double getT_air_in() {
        return t_air_in;
    }

    public void setT_air_in(double t_air_in) {
        this.t_air_in = t_air_in;
    }

    public double getT_air_out() {
        return t_air_out;
    }

    public void setT_air_out(double t_air_out) {
        this.t_air_out = t_air_out;
    }

    public double getT_water_in() {
        return t_water_in;
    }

    public void setT_water_in(double t_water_in) {
        this.t_water_in = t_water_in;
    }

    public double getT_water_out() {
        return t_water_out;
    }

    public void setT_water_out(double t_water_out) {
        this.t_water_out = t_water_out;
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

    public double getT_room() {
        return t_room;
    }

    public void setT_room(double t_room) {
        this.t_room = t_room;
    }

    public double getT_room_target() {
        return t_room_target;
    }

    public void setT_room_target(double t_room_target) {
        this.t_room_target = t_room_target;
    }

    public double getT_thermostat_setpoint() {
        return t_thermostat_setpoint;
    }

    public void setT_thermostat_setpoint(double t_thermostat_setpoint) {
        this.t_thermostat_setpoint = t_thermostat_setpoint;
    }

    public double getCm_mass_power_in() {
        return cm_mass_power_in;
    }

    public void setCm_mass_power_in(double cm_mass_power_in) {
        this.cm_mass_power_in = cm_mass_power_in;
    }

    public double getCm_mass_power_out() {
        return cm_mass_power_out;
    }

    public void setCm_mass_power_out(double cm_mass_power_out) {
        this.cm_mass_power_out = cm_mass_power_out;
    }

    public double getT_water_house_in() {
        return t_water_house_in;
    }

    public void setT_water_house_in(double t_water_house_in) {
        this.t_water_house_in = t_water_house_in;
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
        return state == that.state && Double.compare(t_1, that.t_1) == 0 && Double.compare(t_2, that.t_2) == 0 && Double.compare(fan_power, that.fan_power) == 0 && Double.compare(t_compressor_in, that.t_compressor_in) == 0 && Double.compare(t_compressor_in_transient, that.t_compressor_in_transient) == 0 && Double.compare(t_compressor_out, that.t_compressor_out) == 0 && Double.compare(t_air_in, that.t_air_in) == 0 && Double.compare(t_air_out, that.t_air_out) == 0 && Double.compare(t_water_in, that.t_water_in) == 0 && Double.compare(t_water_out, that.t_water_out) == 0 && Double.compare(t_compressor_out_transient, that.t_compressor_out_transient) == 0 && Double.compare(p_compressor_in, that.p_compressor_in) == 0 && Double.compare(p_compressor_out, that.p_compressor_out) == 0 && Double.compare(rpm, that.rpm) == 0 && Double.compare(fan, that.fan) == 0 && Double.compare(t_inverter, that.t_inverter) == 0 && Double.compare(compressor_power_low_accuracy, that.compressor_power_low_accuracy) == 0 && Double.compare(t_room, that.t_room) == 0 && Double.compare(t_room_target, that.t_room_target) == 0 && Double.compare(cm_mass_power_in, that.cm_mass_power_in) == 0 && Double.compare(cm_mass_power_out, that.cm_mass_power_out) == 0 && Double.compare(t_water_house_in, that.t_water_house_in) == 0 && Double.compare(cm_mass_flow, that.cm_mass_flow) == 0 && Double.compare(ot_boiler_feed_temperature, that.ot_boiler_feed_temperature) == 0 && Double.compare(ot_boiler_return_temperature, that.ot_boiler_return_temperature) == 0 && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, state, t_1, t_2, fan_power, t_compressor_in, t_compressor_in_transient, t_compressor_out, t_air_in, t_air_out, t_water_in, t_water_out, t_compressor_out_transient, p_compressor_in, p_compressor_out, rpm, fan, t_inverter, compressor_power_low_accuracy, t_room, t_room_target, cm_mass_power_in, cm_mass_power_out, t_water_house_in, cm_mass_flow, ot_boiler_feed_temperature, ot_boiler_return_temperature);
    }

    @Override
    public String toString() {
        return "HeatpumpLog{" +
                "timestamp=" + timestamp +
                ", state=" + state +
                ", t_1=" + t_1 +
                ", t_2=" + t_2 +
                ", fan_power=" + fan_power +
                ", t_compressor_in=" + t_compressor_in +
                ", t_compressor_in_transient=" + t_compressor_in_transient +
                ", t_compressor_out=" + t_compressor_out +
                ", t_air_in=" + t_air_in +
                ", t_air_out=" + t_air_out +
                ", t_water_in=" + t_water_in +
                ", t_water_out=" + t_water_out +
                ", t_compressor_out_transient=" + t_compressor_out_transient +
                ", p_compressor_in=" + p_compressor_in +
                ", p_compressor_out=" + p_compressor_out +
                ", rpm=" + rpm +
                ", fan=" + fan +
                ", t_inverter=" + t_inverter +
                ", compressor_power_low_accuracy=" + compressor_power_low_accuracy +
                ", t_room=" + t_room +
                ", t_room_target=" + t_room_target +
                ", cm_mass_power_in=" + cm_mass_power_in +
                ", cm_mass_power_out=" + cm_mass_power_out +
                ", t_water_house_in=" + t_water_house_in +
                ", cm_mass_flow=" + cm_mass_flow +
                ", ot_boiler_feed_temperature=" + ot_boiler_feed_temperature +
                ", ot_boiler_return_temperature=" + ot_boiler_return_temperature +
                '}';
    }
}
