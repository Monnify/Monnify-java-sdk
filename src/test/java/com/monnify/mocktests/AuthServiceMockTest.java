package com.monnify.mocktests;

import com.monnify.BaseMonnifyMockTest;
import com.monnify.services.auth.AuthService;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
class AuthServiceMockTest extends BaseMonnifyMockTest {
    @Test
    void shouldReturnAccessTokenWhenApiReturnsSuccess() {

        stubFor(
                post(urlPathEqualTo("/api/v1/auth/login"))
                        .withHeader("Authorization", matching("Basic .*"))
                        .willReturn(aResponse()
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
                                                + "}"
                                )
                        )
        );

        String token = AuthService.getToken();
        assertNotNull(token);
        assertEquals(ACCESS_TOKEN, token);
        verify(1, postRequestedFor(urlPathEqualTo("/api/v1/auth/login"))
        );
    }
    @Test
    void shouldSendCorrectAuthorizationHeader() {
        stubFor(
                post(urlPathEqualTo("/api/v1/auth/login"))
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


        AuthService.getToken();
        String credentials = API_KEY + ":" + SECRET_KEY;

        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        verify(postRequestedFor(urlPathEqualTo("/api/v1/auth/login"))
                        .withHeader("Authorization", equalTo("Basic " + encodedCredentials)));
    }


    @Test
    void shouldUsePostMethodForAuthentication() {

        stubFor(
                post(urlPathEqualTo("/api/v1/auth/login"))
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


        AuthService.getToken();
        verify(1, postRequestedFor(urlPathEqualTo("/api/v1/auth/login")));
        verify(0, getRequestedFor(urlPathEqualTo("/api/v1/auth/login")));
    }


}