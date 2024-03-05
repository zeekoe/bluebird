package com.github.zeekoe.bluebird.infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class MyHttpClient {
  private static final HttpClient httpClient = HttpClient.newBuilder()
      .version(HttpClient.Version.HTTP_2)
      .connectTimeout(Duration.ofSeconds(10))
      .build();


  public String post(String urlString, Map<String, String> headers, Map<String, String> data) throws IOException, InterruptedException, UnauthorizedException {
    final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
        .POST(HttpRequest.BodyPublishers.ofString(encodeFormData(data)))
        .uri(URI.create(urlString));

    for (Map.Entry<String, String> entry : headers.entrySet()) {
      requestBuilder.setHeader(entry.getKey(), entry.getValue());
    }

    final HttpResponse<InputStream> httpResponse = httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofInputStream());
    final int statusCode = httpResponse.statusCode();
    if (statusCode >= 400 && statusCode < 404) {
      throw new UnauthorizedException(decodeResponseBody(httpResponse));
    }
    if (statusCode != 200) {
      System.out.println(decodeResponseBody(httpResponse));
      throw new RuntimeException("Incorrect response: " + httpResponse);
    }

    return decodeResponseBody(httpResponse);
  }

  public String get(String url, String token) throws IOException, InterruptedException, UnauthorizedException {
    final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create(url))
        .setHeader("Authorization", "Bearer " + token);

    final HttpResponse<InputStream> httpResponse = httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofInputStream());
    final int statusCode = httpResponse.statusCode();
    if (statusCode >= 400 && statusCode < 404) {
      throw new UnauthorizedException(decodeResponseBody(httpResponse));
    }
    if (statusCode != 200) {
      System.out.println(decodeResponseBody(httpResponse));
      throw new RuntimeException("Incorrect response: " + httpResponse);
    }

    return decodeResponseBody(httpResponse);
  }

  public static String decodeResponseBody(HttpResponse<InputStream> httpResponse) throws IOException {
    if (httpResponse.headers().firstValue("Content-Encoding").orElse("").equals("gzip")) {
      try (InputStream inputStream = new GZIPInputStream(httpResponse.body())) {
        return new String(inputStream.readAllBytes());
      }
    } else {
      final String responseBody = new String(httpResponse.body().readAllBytes());
      httpResponse.body().close();
      return responseBody;
    }
  }

  private static String encodeFormData(Map<String, String> data) {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, String> entry : data.entrySet()) {
      if (sb.length() > 0) {
        sb.append("&");
      }
      sb.append(entry.getKey())
          .append("=")
          .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
    }
    return sb.toString();
  }

  public static class UnauthorizedException extends Exception {
    public UnauthorizedException(String message) {
      super(message);
    }
  }
}
