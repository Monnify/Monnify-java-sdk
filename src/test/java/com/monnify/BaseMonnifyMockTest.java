package com.monnify;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;
import com.monnify.services.auth.AuthService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.reset;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

public abstract class BaseMonnifyMockTest {

    protected final Gson gson = new Gson();

    protected static WireMockServer wireMockServer;

    protected static final String API_KEY = "MK_TEST_API_KEY";
    protected static final String SECRET_KEY = "test-secret-key";
    protected static final String ACCESS_TOKEN = "mock-access-token";

    @BeforeAll
    static void setUpWireMock() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        configureFor("localhost", 8089);
        Monnify.initialize(API_KEY, SECRET_KEY);
        Monnify.setBaseUrlForTesting("http://localhost:8089");
    }

    @BeforeEach
    void resetWireMock() {
        reset();
        AuthService.resetForTesting();
        mockAuthentication();

    }
    protected void mockAuthentication() {

        stubFor(
                post(urlPathEqualTo("/api/v1/auth/login"))
                        .withHeader("Authorization", matching("Basic .*"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\": true,"
                                                        + "\"responseMessage\": \"success\","
                                                        + "\"responseCode\": \"0\","
                                                        + "\"responseBody\": {"
                                                        + "\"accessToken\": \"mock-access-token\","
                                                        + "\"expiresIn\": 3600"
                                                        + "}"
                                                        + "}")));
    }

    @AfterAll
    static void tearDownWireMock() {

        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }
}