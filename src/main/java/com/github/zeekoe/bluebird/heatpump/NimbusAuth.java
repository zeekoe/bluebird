package com.github.zeekoe.bluebird.heatpump;

import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.pkce.CodeChallengeMethod;
import com.nimbusds.oauth2.sdk.pkce.CodeVerifier;
import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import com.nimbusds.openid.connect.sdk.Nonce;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.*;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public class NimbusAuth {
    private static HttpClient httpClient;

    private static String USERNAME;
    private static String PASSWORD;

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("/etc/bluebird.config"));
            USERNAME = properties.getProperty("bluebird.username");
            PASSWORD = properties.getProperty("bluebird.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        CookieManager cm = new CookieManager();
        CookieHandler.setDefault(cm);

        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .cookieHandler(cm)
                .build();

        String request = "{\"grant_type\": \"password\", \"client_id\": \"WeheatCommunityAPI\" \"email\":\"" + USERNAME.replace("@", "%40") + "\",\"password\":\"" + PASSWORD + "\",\"scope\": \"openid\"}";

        final HttpRequest.Builder builder42 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(request))
                .uri(URI.create("https://auth.weheat.nl/auth/realms/Weheat/protocol/openid-connect/token?grant_type=password"))
                .setHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/116.0");

        final HttpResponse<InputStream> httpResponse2 = httpClient.send(builder42.build(), HttpResponse.BodyHandlers.ofInputStream());
        System.out.println(httpResponse2.headers().firstValue("location"));
        final String s2 = new String(getDecodedInputStream(httpResponse2).readAllBytes());

        System.out.println(s2);
    }

    private static String doRequest(String url) throws IOException, InterruptedException {
        final HttpRequest.Builder builder4 = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .setHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/116.0");

        final HttpResponse<InputStream> httpResponse = httpClient.send(builder4.build(), HttpResponse.BodyHandlers.ofInputStream());
        System.out.println(httpResponse.headers().firstValue("location"));
        return new String(getDecodedInputStream(httpResponse).readAllBytes());
    }

    public static InputStream getDecodedInputStream(
            HttpResponse<InputStream> httpResponse) {
        String encoding = determineContentEncoding(httpResponse);
        try {
            switch (encoding) {
                case "":
                    return httpResponse.body();
                case "gzip":
                    return new GZIPInputStream(httpResponse.body());
                default:
                    throw new UnsupportedOperationException(
                            "Unexpected Content-Encoding: " + encoding);
            }
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    public static String determineContentEncoding(
            HttpResponse<?> httpResponse) {
        return httpResponse.headers().firstValue("Content-Encoding").orElse("");
    }
}
