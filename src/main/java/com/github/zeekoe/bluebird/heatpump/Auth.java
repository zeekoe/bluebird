package com.github.zeekoe.bluebird.heatpump;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zeekoe.bluebird.heatpump.model.Token;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Properties;

public class Auth {
    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("/etc/bluebird.config"));
            USERNAME = properties.getProperty("bluebird.username");
            PASSWORD = properties.getProperty("bluebird.password");
            API_KEY = properties.getProperty("bluebird.apikey");
            LOG_URL = properties.getProperty("bluebird.logurl");
            TOKEN_URL = properties.getProperty("bluebird.tokenurl");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private Token token = null;

    private static String USERNAME;
    private static String PASSWORD;
    private static String API_KEY;
    private static String LOG_URL;
    private static String TOKEN_URL;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public String getApikey() {
        return API_KEY;
    }

    public String getLogurl() {
        return LOG_URL;
    }

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
        String url = TOKEN_URL + "?grant_type=refresh_token";
        String request = "{\"refresh_token\":\"" + token.getRefresh_token() + "\"}";
        return doRequest(request, url);
    }

    private String doLogin() throws IOException, InterruptedException {
        String url = TOKEN_URL + "?grant_type=password";
        String request = "{\"email\":\"" + USERNAME + "\",\"password\":\"" + PASSWORD + "\",\"gotrue_meta_security\":{}}";
        System.out.print(request);
        return doRequest(request, url);
    }

    private static String doRequest(String requestPayload, String loginUrl) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestPayload))
                .uri(URI.create(loginUrl))
                .setHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/116.0") // add request header
                .setHeader("Content-Type", "application/json;charset=UTF-8")
                .setHeader("apikey", API_KEY)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200) {
            throw new RuntimeException("Incorrect status code in token retrieval " + response);
        }

        return response.body();
    }
}
