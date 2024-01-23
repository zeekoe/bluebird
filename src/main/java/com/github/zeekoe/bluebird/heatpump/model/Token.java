package com.github.zeekoe.bluebird.heatpump.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Token {
    private LocalDateTime tokenTime;
    private String access_token;
    private int expires_in;
    private String refresh_token;

    public Token() {
        this.tokenTime = LocalDateTime.now();
    }

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

    public LocalDateTime getExpiryDateTime() {
        return tokenTime.plusMinutes(expires_in);
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
        Token token = (Token) o;
        return expires_in == token.expires_in && Objects.equals(tokenTime, token.tokenTime) && Objects.equals(access_token, token.access_token) && Objects.equals(refresh_token, token.refresh_token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenTime, access_token, expires_in, refresh_token);
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
