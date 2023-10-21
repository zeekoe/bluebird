package com.github.zeekoe.bluebird.heatpump.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Token {
    private String access_token;
    private int expires_in;
    private int expires_at;
    private String refresh_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public int getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(int expires_at) {
        this.expires_at = expires_at;
    }

    public LocalDateTime getExpiryDateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(expires_at), ZoneId.systemDefault());
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token that = (Token) o;
        return expires_in == that.expires_in && expires_at == that.expires_at && Objects.equals(access_token, that.access_token) && Objects.equals(refresh_token, that.refresh_token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(access_token, expires_in, expires_at, refresh_token);
    }

    @Override
    public String toString() {
        return new StringJoiner(",\n", Token.class.getSimpleName() + "[", "]")
                .add("access_token='" + access_token + "'")
                .add("expires_in=" + expires_in)
                .add("expires_at=" + getExpiryDateTime())
                .add("refresh_token='" + refresh_token + "'")
                .toString();
    }
}
