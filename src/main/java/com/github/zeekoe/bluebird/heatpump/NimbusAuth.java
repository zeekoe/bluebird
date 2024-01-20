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

        final HttpRequest.Builder builder41 = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://portal.weheat.nl/"))
                .setHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/116.0");

        final HttpResponse<InputStream> httpResponse1 = httpClient.send(builder41.build(), HttpResponse.BodyHandlers.ofInputStream());
        System.out.println(httpResponse1.headers().firstValue("location"));
        final String s = new String(getDecodedInputStream(httpResponse1).readAllBytes());
        System.out.println(s);

        final String s1 = doRequest("https://portal.weheat.nl/signin?callbackUrl=%2F");

        final CodeVerifier codeVerifier = new CodeVerifier();
// The client ID provisioned by the OpenID provider when
// the client was registered
        ClientID clientID = new ClientID("weheat-portal");

// The client callback URL
        URI callback = new URI("https://portal.weheat.nl/api/auth/callback/keycloak");

// Generate random state string to securely pair the callback to this request
        State state = new State();

// Generate nonce for the ID token
        Nonce nonce = new Nonce();

// Compose the OpenID authentication request (for the code flow)
        AuthenticationRequest request = new AuthenticationRequest.Builder(
                new ResponseType("code"),
                new Scope("openid", "email", "profile"),
                clientID,
                callback)
                .endpointURI(new URI("https://auth.weheat.nl/auth/realms/Weheat/protocol/openid-connect/auth"))
                .state(state)
                .nonce(nonce).codeChallenge(codeVerifier, CodeChallengeMethod.S256)
                .build();


        System.out.println(request.toURI());

        String loginUrl = null;

        final HTTPResponse response = request.toHTTPRequest().send();
        final String[] bodyLines = response.getBody().split("\n");
        for (String bodyLine : bodyLines) {
            if (bodyLine.contains("\"loginAction\"")) {
                final String trimmed = bodyLine.replace("\"loginAction\"", "").trim();
                String strPattern = "\"[^\"]*\"";

                Pattern pattern = Pattern.compile(strPattern);
                Matcher matcher = pattern.matcher(trimmed);

                matcher.find();
                loginUrl = matcher.group().replace("\"","");
            }
        }
        System.out.println(response);

        final String cookieHeader = cm.getCookieStore().get(new URI("https://auth.weheat.nl")).stream().map(c -> c.getName() + "=" + c.getValue()).collect(Collectors.joining(";"));

        final HttpRequest.Builder builder = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString("username=" + USERNAME.replace("@", "%40") + "&password=" + PASSWORD + "&credentialId="))
                .uri(URI.create(loginUrl))
                .setHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/116.0") // add request header
                .setHeader("Content-Type", "application/x-www-form-urlencoded");
//                .setHeader("Cookie", cookieHeader);

        HttpResponse<String> response2 = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());

        final String keyCloakUrl = response2.headers().firstValue("location").orElseThrow();

        final HttpRequest.Builder builder4 = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(keyCloakUrl))
                .setHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/116.0");

        final HttpResponse<InputStream> httpResponse = httpClient.send(builder4.build(), HttpResponse.BodyHandlers.ofInputStream());
        System.out.println(httpResponse.headers().firstValue("location"));
        String response3 = new String(getDecodedInputStream(httpResponse).readAllBytes());


        System.out.println(response3);

//        String signinUrl = "https://portal.weheat.nl/signin?callbackUrl=%2F";


//        String response4 = doRequest(signinUrl);

//        System.out.println(response4);

        String token = "";

        String sessionUrl = "https://portal.weheat.nl/api/auth/session";

        final HttpRequest requestbla = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(sessionUrl))
                .setHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/116.0").build();

        final HttpResponse<String> requestblares = httpClient.send(requestbla, HttpResponse.BodyHandlers.ofString());


        String hpLogUrl = "https://api.weheat.nl/api/v1/heat-pumps/7c47de4f-5d0e-42e2-a6e2-fd2dba8a3c04/logs/latest";

        final HttpRequest.Builder builder5 = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(hpLogUrl))
                .header("Authorization", "Bearer " + token)
                .setHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/116.0");
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
