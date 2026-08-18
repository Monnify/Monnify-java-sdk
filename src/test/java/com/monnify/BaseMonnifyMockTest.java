package com.monnify;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;
import com.monnify.services.auth.AuthService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public abstract class BaseMonnifyMockTest {

    protected static WireMockServer wireMockServer;

    protected static final String API_KEY = "MK_TEST_API_KEY";
    protected static final String SECRET_KEY = "test-secret-key";
    protected static final String ACCESS_TOKEN = "mock-access-token";

    private static volatile boolean initialized = false;

    @BeforeAll
    static synchronized void setUpWireMock() {

        if (initialized) {
            return;
        }

        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();

        configureFor("localhost", 8089);

        Monnify.initialize(API_KEY, SECRET_KEY);
        Monnify.setBaseUrlForTesting("http://localhost:8089");

        initialized = true;
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
                                        .withHeader(
                                                "Content-Type",
                                                "application/json"
                                        )
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\": true,"
                                                        + "\"responseMessage\": \"success\","
                                                        + "\"responseCode\": \"0\","
                                                        + "\"responseBody\": {"
                                                        + "\"accessToken\": \"mock-access-token\","
                                                        + "\"expiresIn\": 3600"
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );
    }
}