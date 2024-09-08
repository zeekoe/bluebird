package com.github.zeekoe.bluebird.heatpump;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zeekoe.bluebird.heatpump.model.Token;
import com.github.zeekoe.bluebird.infrastructure.MyHttpClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import static com.github.zeekoe.bluebird.infrastructure.BluebirdProperties.property;
import static com.github.zeekoe.bluebird.infrastructure.BluebirdProperty.WEHEAT_PASSWORD;
import static com.github.zeekoe.bluebird.infrastructure.BluebirdProperty.WEHEAT_USERNAME;

public class Auth {
  private static final MyHttpClient httpClient = new MyHttpClient();
  private Token token = null;

  private static final String TOKEN_URL = "https://auth.weheat.nl/auth/realms/Weheat/protocol/openid-connect/token";
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public String getToken() throws IOException, InterruptedException {
    if (token == null) {
      System.out.println("Retrieving token");
      token = OBJECT_MAPPER.readValue(doLogin(), Token.class);
    }
    if (token.getExpiryDateTime().minusMinutes(1).isBefore(LocalDateTime.now())) {
      System.out.println("Refreshing token");
      token = OBJECT_MAPPER.readValue(refreshToken(), Token.class);
    }
    return token.getAccess_token();
  }

  private String refreshToken() throws IOException, InterruptedException {
    try {
      return httpClient.post(TOKEN_URL,
          Map.of("Content-Type", "application/x-www-form-urlencoded",
              "Accept", "application/json"),
          Map.of(
              "grant_type", "refresh_token",
              "scope", "openid",
              "client_id", "WeheatCommunityAPI",
              "refresh_token", token.getRefresh_token()
          ));
    } catch (MyHttpClient.UnauthorizedException e) {
      token = null;
      throw new RuntimeException(e);
    }
  }

  private String doLogin() throws IOException, InterruptedException {
    try {
      return httpClient.post(TOKEN_URL,
          Map.of("Content-Type", "application/x-www-form-urlencoded",
              "Accept", "application/json"),
          Map.of(
              "grant_type", "password",
              "scope", "openid",
              "client_id", "WeheatCommunityAPI",
              "username", property(WEHEAT_USERNAME),
              "password", property(WEHEAT_PASSWORD)
          ));
    } catch (MyHttpClient.UnauthorizedException e) {
      token = null;
      throw new RuntimeException(e);
    }
  }

  public void invalidateToken() {
    System.out.println("Invalidating token");
    this.token = null;
  }
}
