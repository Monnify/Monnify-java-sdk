package com.monnify.mocktests;

import com.monnify.BaseMonnifyMockTest;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.paycode.PaycodeRequest;
import com.monnify.models.paycode.PaycodeResponse;
import com.monnify.services.paycode.PaycodeService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class PaycodeServiceMockTest extends BaseMonnifyMockTest {

    private final PaycodeService paycodeService = new PaycodeService();

    @Test
    void shouldCreatePaycode() {
        stubFor(post(urlEqualTo("/api/v1/paycode"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{"
                                        + "\"requestSuccessful\":true,"
                                        + "\"responseMessage\":\"success\","
                                        + "\"responseCode\":\"0\","
                                        + "\"responseBody\":{"
                                        + "\"paycode\":\"123456\","
                                        + "\"transactionReference\":\"MNFY|123456\","
                                        + "\"paycodeReference\":\"PAY-123\","
                                        + "\"beneficiaryName\":\"John Doe\","
                                        + "\"amount\":1000,"
                                        + "\"fee\":10,"
                                        + "\"transactionStatus\":\"PENDING\","
                                        + "\"expiryDate\":\"2026-08-20T12:00:00\","
                                        + "\"createdOn\":\"2026-08-14T10:00:00\","
                                        + "\"createdBy\":\"test\","
                                        + "\"modifiedBy\":\"test\""
                                        + "}"
                                        + "}")));

        PaycodeRequest request = PaycodeRequest.builder()
                .beneficiaryName("John Doe")
                .amount(BigDecimal.valueOf(1000))
                .paycodeReference("PAY-123")
                .expiryDate("2026-08-20")
                .clientId("client-123")
                .build();

        MonnifyBaseResponse<PaycodeResponse> response =
                paycodeService.createPaycode(request);

        assertTrue(response.isRequestSuccessful());
        assertEquals("0", response.getResponseCode());
        assertNotNull(response.getResponseBody());
        assertEquals("123456", response.getResponseBody().getPaycode());
        assertEquals("PAY-123", response.getResponseBody().getPaycodeReference());
        assertEquals("John Doe", response.getResponseBody().getBeneficiaryName());
        assertEquals(BigDecimal.valueOf(1000), response.getResponseBody().getAmount());
    }

    @Test
    void shouldRejectInvalidPaycodeRequest() {
        PaycodeRequest request = PaycodeRequest.builder().build();

        assertThrows(
                MonnifyValidationException.class,
                () -> paycodeService.createPaycode(request)
        );
    }

    @Test
    void shouldGetPaycode() {
        stubFor(get(urlEqualTo("/api/v1/paycode/PAY-123"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{"
                                        + "\"requestSuccessful\":true,"
                                        + "\"responseMessage\":\"success\","
                                        + "\"responseCode\":\"0\","
                                        + "\"responseBody\":{"
                                        + "\"paycode\":\"123456\","
                                        + "\"transactionReference\":\"MNFY|123456\","
                                        + "\"paycodeReference\":\"PAY-123\","
                                        + "\"beneficiaryName\":\"John Doe\","
                                        + "\"amount\":1000,"
                                        + "\"fee\":10,"
                                        + "\"transactionStatus\":\"PENDING\""
                                        + "}"
                                        + "}")));

        MonnifyBaseResponse<PaycodeResponse> response =
                paycodeService.getPaycode("PAY-123");

        assertTrue(response.isRequestSuccessful());
        assertEquals("0", response.getResponseCode());
        assertNotNull(response.getResponseBody());
        assertEquals("123456", response.getResponseBody().getPaycode());
        assertEquals("PAY-123", response.getResponseBody().getPaycodeReference());
    }

    @Test
    void shouldEncodePaycodeReferenceInUrlPath() {
        stubFor(get(urlEqualTo("/api/v1/paycode/PAY%2B123"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{"
                                        + "\"requestSuccessful\":true,"
                                        + "\"responseMessage\":\"success\","
                                        + "\"responseCode\":\"0\","
                                        + "\"responseBody\":{"
                                        + "\"paycode\":\"123456\","
                                        + "\"paycodeReference\":\"PAY+123\","
                                        + "\"beneficiaryName\":\"John Doe\","
                                        + "\"amount\":1000,"
                                        + "\"transactionStatus\":\"PENDING\""
                                        + "}"
                                        + "}")));

        MonnifyBaseResponse<PaycodeResponse> response =
                paycodeService.getPaycode("PAY+123");

        assertTrue(response.isRequestSuccessful());
        assertNotNull(response.getResponseBody());
        assertEquals("PAY+123", response.getResponseBody().getPaycodeReference());

        verify(getRequestedFor(urlEqualTo("/api/v1/paycode/PAY%2B123")));
    }

    @Test
    void shouldRejectEmptyPaycodeReferenceWhenGettingPaycode() {
        assertThrows(
                MonnifyValidationException.class,
                () -> paycodeService.getPaycode("")
        );
    }

    @Test
    void shouldRejectNullPaycodeReferenceWhenGettingPaycode() {
        assertThrows(
                MonnifyValidationException.class,
                () -> paycodeService.getPaycode(null)
        );
    }

    @Test
    void shouldGetClearPaycode() {
        stubFor(get(urlEqualTo("/api/v1/paycode/PAY-123/authorize"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{"
                                        + "\"requestSuccessful\":true,"
                                        + "\"responseMessage\":\"success\","
                                        + "\"responseCode\":\"0\","
                                        + "\"responseBody\":{"
                                        + "\"paycode\":\"123456\","
                                        + "\"paycodeReference\":\"PAY-123\","
                                        + "\"beneficiaryName\":\"John Doe\","
                                        + "\"amount\":1000,"
                                        + "\"transactionStatus\":\"AUTHORIZED\""
                                        + "}"
                                        + "}")));

        MonnifyBaseResponse<PaycodeResponse> response =
                paycodeService.getClearPaycode("PAY-123");

        assertTrue(response.isRequestSuccessful());
        assertEquals("0", response.getResponseCode());
        assertNotNull(response.getResponseBody());
        assertEquals("123456", response.getResponseBody().getPaycode());
        assertEquals("AUTHORIZED", response.getResponseBody().getTransactionStatus());
    }

    @Test
    void shouldRejectEmptyPaycodeReferenceWhenClearingPaycode() {
        assertThrows(
                MonnifyValidationException.class,
                () -> paycodeService.getClearPaycode("")
        );
    }

    @Test
    void shouldFetchPaycodes() {
        stubFor(get(urlPathEqualTo("/api/v1/paycode"))
                .withQueryParam("transactionReference", equalTo("MNFY|123456"))
                .withQueryParam("beneficiaryName", equalTo("John Doe"))
                .withQueryParam("transactionStatus", equalTo("PENDING"))
                .withQueryParam("from", equalTo("100"))
                .withQueryParam("to", equalTo("200"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{"
                                        + "\"requestSuccessful\":true,"
                                        + "\"responseMessage\":\"success\","
                                        + "\"responseCode\":\"0\","
                                        + "\"responseBody\":{"
                                        + "\"content\":[{"
                                        + "\"paycode\":\"123456\","
                                        + "\"transactionReference\":\"MNFY|123456\","
                                        + "\"paycodeReference\":\"PAY-123\","
                                        + "\"beneficiaryName\":\"John Doe\","
                                        + "\"amount\":1000,"
                                        + "\"fee\":10,"
                                        + "\"transactionStatus\":\"PENDING\""
                                        + "}],"
                                        + "\"pageNumber\":0,"
                                        + "\"pageSize\":10,"
                                        + "\"totalElements\":1,"
                                        + "\"totalPages\":1"
                                        + "}"
                                        + "}")));

        MonnifyBaseResponse<SearchResponse<PaycodeResponse>> response =
                paycodeService.fetchPaycodes(
                        "MNFY|123456",
                        "John Doe",
                        "PENDING",
                        100,
                        200
                );

        assertTrue(response.isRequestSuccessful());
        assertEquals("0", response.getResponseCode());
        assertNotNull(response.getResponseBody());
        assertNotNull(response.getResponseBody().getContent());
        assertEquals(1, response.getResponseBody().getContent().size());
        assertEquals(
                "PAY-123",
                response.getResponseBody().getContent().get(0).getPaycodeReference()
        );
    }

    @Test
    void shouldFetchPaycodesWithoutOptionalParameters() {
        stubFor(get(urlPathEqualTo("/api/v1/paycode"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{"
                                        + "\"requestSuccessful\":true,"
                                        + "\"responseMessage\":\"success\","
                                        + "\"responseCode\":\"0\","
                                        + "\"responseBody\":{"
                                        + "\"content\":[],"
                                        + "\"pageNumber\":0,"
                                        + "\"pageSize\":10,"
                                        + "\"totalElements\":0,"
                                        + "\"totalPages\":0"
                                        + "}"
                                        + "}")));

        MonnifyBaseResponse<SearchResponse<PaycodeResponse>> response =
                paycodeService.fetchPaycodes(null, null, null, 0, 0);

        assertTrue(response.isRequestSuccessful());
        assertNotNull(response.getResponseBody());
        assertNotNull(response.getResponseBody().getContent());
        assertTrue(response.getResponseBody().getContent().isEmpty());
    }

    @Test
    void shouldDeletePaycode() {
        stubFor(delete(urlEqualTo("/api/v1/paycode/PAY-123"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{"
                                        + "\"requestSuccessful\":true,"
                                        + "\"responseMessage\":\"success\","
                                        + "\"responseCode\":\"0\","
                                        + "\"responseBody\":{"
                                        + "\"paycode\":\"123456\","
                                        + "\"paycodeReference\":\"PAY-123\","
                                        + "\"beneficiaryName\":\"John Doe\","
                                        + "\"amount\":1000,"
                                        + "\"transactionStatus\":\"DELETED\""
                                        + "}"
                                        + "}")));

        MonnifyBaseResponse<PaycodeResponse> response =
                paycodeService.deletePaycode("PAY-123");

        assertTrue(response.isRequestSuccessful());
        assertEquals("0", response.getResponseCode());
        assertNotNull(response.getResponseBody());
        assertEquals("PAY-123", response.getResponseBody().getPaycodeReference());
        assertEquals("DELETED", response.getResponseBody().getTransactionStatus());
    }

    @Test
    void shouldRejectEmptyPaycodeReferenceWhenDeletingPaycode() {
        assertThrows(
                MonnifyValidationException.class,
                () -> paycodeService.deletePaycode("")
        );
    }

    @Test
    void shouldRejectNullPaycodeReferenceWhenDeletingPaycode() {
        assertThrows(
                MonnifyValidationException.class,
                () -> paycodeService.deletePaycode(null)
        );
    }
}