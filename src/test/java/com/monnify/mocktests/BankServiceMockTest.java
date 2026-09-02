package com.monnify.mocktests;


import com.monnify.BaseMonnifyMockTest;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.bank.BankResponse;
import com.monnify.services.bank.BankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BankServiceMockTest extends BaseMonnifyMockTest {

    private BankService bankService;

    @BeforeEach
    void setUp() {
        bankService = new BankService();
    }

    @Test
    void shouldReturnBanksWhenApiReturnsSuccess() {

        stubFor(get(urlPathEqualTo("/api/v1/banks"))
                        .willReturn(aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(
                                                "{" + "\"requestSuccessful\": true,"
                                                        + "\"responseMessage\": \"success\","
                                                        + "\"responseCode\": \"0\","
                                                        + "\"responseBody\": ["
                                                        + "{"
                                                        + "\"code\": \"044\","
                                                        + "\"name\": \"Access Bank\","
                                                        + "\"ussdTemplate\": \"*901*\","
                                                        + "\"baseUssdCode\": \"*901#\","
                                                        + "\"transferUssdTemplate\": \"*901*AMOUNT*ACCOUNT#\""
                                                        + "},"
                                                        + "{"
                                                        + "\"code\": \"058\","
                                                        + "\"name\": \"Guaranty Trust Bank\","
                                                        + "\"ussdTemplate\": \"*737*\","
                                                        + "\"baseUssdCode\": \"*737#\","
                                                        + "\"transferUssdTemplate\": \"*737*AMOUNT*ACCOUNT#\""
                                                        + "}"
                                                        + "]"
                                                        + "}")));

        MonnifyBaseResponse<List<BankResponse>> response = bankService.getBanks();
        assertNotNull(response);
        assertEquals("0", response.getResponseCode());
        assertEquals("success", response.getResponseMessage());
        assertNotNull(response.getResponseBody());
        assertEquals(2, response.getResponseBody().size());
        BankResponse firstBank = response.getResponseBody().get(0);
        assertEquals("044", firstBank.code);
        assertEquals("Access Bank", firstBank.getName());
        assertEquals("*901*", firstBank.getUssdTemplate());
        assertEquals("*901#", firstBank.getBaseUssdCode());
        assertEquals("*901*AMOUNT*ACCOUNT#", firstBank.getTransferUssdTemplate());
        BankResponse secondBank = response.getResponseBody().get(1);
        assertEquals("058", secondBank.code);

        assertEquals("Guaranty Trust Bank", secondBank.getName());

        verify(
                1,
                getRequestedFor(
                        urlPathEqualTo("/api/v1/banks")
                )
        );
    }


    @Test
    void shouldReturnEmptyListWhenApiReturnsNoBanks() {

        stubFor(get(urlPathEqualTo("/api/v1/banks"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody("{"
                                                        + "\"requestSuccessful\": true,"
                                                        + "\"responseMessage\": \"success\","
                                                        + "\"responseCode\": \"0\","
                                                        + "\"responseBody\": []"
                                                        + "}")));

        MonnifyBaseResponse<List<BankResponse>> response = bankService.getBanks();
        assertNotNull(response);
        assertEquals("0", response.getResponseCode());
        assertNotNull(response.getResponseBody());
        assertTrue(response.getResponseBody().isEmpty());
        verify(1, getRequestedFor(urlPathEqualTo("/api/v1/banks")));
    }


    @Test
    void shouldReturnBanksWithUssdShortCode() {

        stubFor(
                get(urlPathEqualTo("/api/v1/sdk/transactions/banks"))
                        .willReturn(aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\": true,"
                                                        + "\"responseMessage\": \"success\","
                                                        + "\"responseCode\": \"0\","
                                                        + "\"responseBody\": ["
                                                        + "{"
                                                        + "\"code\": \"044\","
                                                        + "\"name\": \"Access Bank\","
                                                        + "\"ussdTemplate\": \"*901*\","
                                                        + "\"baseUssdCode\": \"*901#\","
                                                        + "\"transferUssdTemplate\": \"*901*AMOUNT*ACCOUNT#\""
                                                        + "}"
                                                        + "]"
                                                        + "}"
                                        )
                        )
        );

        MonnifyBaseResponse<List<BankResponse>> response = bankService.getBanksWithUssdShortCode();
        assertNotNull(response);
        assertEquals("0", response.getResponseCode());
        assertNotNull(response.getResponseBody());
        assertEquals(1, response.getResponseBody().size());
        BankResponse bank = response.getResponseBody().get(0);
        assertEquals("044", bank.code);
        assertEquals("Access Bank", bank.getName());
        assertEquals("*901*", bank.getUssdTemplate());
        assertEquals("*901#", bank.getBaseUssdCode());
        assertEquals("*901*AMOUNT*ACCOUNT#", bank.getTransferUssdTemplate());

        verify(1, getRequestedFor(urlPathEqualTo("/api/v1/sdk/transactions/banks")));
    }


    @Test
    void shouldReturnEmptyListForBanksWithUssdShortCode() {

        stubFor(get(urlPathEqualTo("/api/v1/sdk/transactions/banks"))
                        .willReturn(aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\": true,"
                                                        + "\"responseMessage\": \"success\","
                                                        + "\"responseCode\": \"0\","
                                                        + "\"responseBody\": []"
                                                        + "}")));

        MonnifyBaseResponse<List<BankResponse>> response = bankService.getBanksWithUssdShortCode();
        assertNotNull(response);
        assertNotNull(response.getResponseBody());
        assertTrue(response.getResponseBody().isEmpty());
        verify(1, getRequestedFor(urlPathEqualTo("/api/v1/sdk/transactions/banks")));
    }
}