package com.monnify.mocktests;

import com.monnify.BaseMonnifyMockTest;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.refund.RefundRequest;
import com.monnify.models.refund.RefundResponse;
import com.monnify.services.refund.RefundService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

class RefundServiceMockTest extends BaseMonnifyMockTest {

    private final RefundService refundService = new RefundService();

    @Test
    void shouldInitiateRefund() {

        stubFor(
                post(urlPathEqualTo("/api/v1/refunds/initiate-refund"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\":true,"
                                                        + "\"responseMessage\":\"Refund initiated successfully\","
                                                        + "\"responseCode\":\"0\","
                                                        + "\"responseBody\":{"
                                                        + "\"refundReference\":\"REF-123456\","
                                                        + "\"transactionReference\":\"TXN-123456\","
                                                        + "\"refundAmount\":1000.00,"
                                                        + "\"refundStatus\":\"PENDING\""
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        RefundRequest request = RefundRequest.builder()
                .transactionReference("TXN-123456")
                .refundReference("REF-123456")
                .refundAmount(new BigDecimal("1000.00"))
                .refundReason("Customer requested refund")
                .customerNote("Refund for failed transaction")
                .build();

        MonnifyBaseResponse<RefundResponse> response =
                refundService.initiateRefund(request);

        assertNotNull(response);
        assertTrue(response.isRequestSuccessful());
        assertEquals("0", response.getResponseCode());
        assertNotNull(response.getResponseBody());
        assertEquals("REF-123456",
                response.getResponseBody().getRefundReference());
        assertEquals("TXN-123456",
                response.getResponseBody().getTransactionReference());

        verify(
                postRequestedFor(
                        urlPathEqualTo("/api/v1/refunds/initiate-refund")
                )
        );
    }

    @Test
    void shouldGetRefundStatus() {

        stubFor(
                get(urlPathEqualTo("/api/v1/refunds/REF-123456"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\":true,"
                                                        + "\"responseMessage\":\"Refund status retrieved successfully\","
                                                        + "\"responseCode\":\"0\","
                                                        + "\"responseBody\":{"
                                                        + "\"refundReference\":\"REF-123456\","
                                                        + "\"transactionReference\":\"TXN-123456\","
                                                        + "\"refundAmount\":1000.00,"
                                                        + "\"refundStatus\":\"COMPLETED\""
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        MonnifyBaseResponse<RefundResponse> response =
                refundService.getRefundStatus("REF-123456");

        assertNotNull(response);
        assertTrue(response.isRequestSuccessful());
        assertEquals("0", response.getResponseCode());
        assertNotNull(response.getResponseBody());
        assertEquals("REF-123456",
                response.getResponseBody().getRefundReference());
        assertEquals("COMPLETED",
                response.getResponseBody().getRefundStatus());

        verify(
                getRequestedFor(
                        urlPathEqualTo("/api/v1/refunds/REF-123456")
                )
        );
    }

    @Test
    void shouldGetRefunds() {

        stubFor(
                get(urlPathEqualTo("/api/v1/refunds"))
                        .withQueryParam("page", equalTo("0"))
                        .withQueryParam("size", equalTo("10"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\":true,"
                                                        + "\"responseMessage\":\"Refunds retrieved successfully\","
                                                        + "\"responseCode\":\"0\","
                                                        + "\"responseBody\":{"
                                                        + "\"content\":["
                                                        + "{"
                                                        + "\"refundReference\":\"REF-123456\","
                                                        + "\"transactionReference\":\"TXN-123456\","
                                                        + "\"refundAmount\":1000.00,"
                                                        + "\"refundStatus\":\"COMPLETED\""
                                                        + "}"
                                                        + "],"
                                                        + "\"pageSize\":10,"
                                                        + "\"pageNumber\":0,"
                                                        + "\"totalPages\":1,"
                                                        + "\"totalElements\":1"
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        MonnifyBaseResponse<SearchResponse<RefundResponse>> response =
                refundService.getRefunds(0, 10);

        assertNotNull(response);
        assertTrue(response.isRequestSuccessful());
        assertEquals("0", response.getResponseCode());
        assertNotNull(response.getResponseBody());
        assertNotNull(response.getResponseBody().getContent());
        assertEquals(1, response.getResponseBody().getContent().size());

        RefundResponse refund =
                response.getResponseBody().getContent().get(0);

        assertEquals("REF-123456", refund.getRefundReference());
        assertEquals("TXN-123456", refund.getTransactionReference());
        assertEquals(new BigDecimal("1000.00"), refund.getRefundAmount());
        assertEquals("COMPLETED", refund.getRefundStatus());

        verify(
                getRequestedFor(
                        urlPathEqualTo("/api/v1/refunds")
                )
                        .withQueryParam("page", equalTo("0"))
                        .withQueryParam("size", equalTo("10"))
        );
    }

    @Test
    void shouldGetRefundsWithoutPagination() {

        stubFor(
                get(urlPathEqualTo("/api/v1/refunds"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\":true,"
                                                        + "\"responseMessage\":\"Refunds retrieved successfully\","
                                                        + "\"responseCode\":\"0\","
                                                        + "\"responseBody\":{"
                                                        + "\"content\":[],"
                                                        + "\"pageSize\":0,"
                                                        + "\"pageNumber\":0,"
                                                        + "\"totalPages\":0,"
                                                        + "\"totalElements\":0"
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        MonnifyBaseResponse<SearchResponse<RefundResponse>> response =
                refundService.getRefunds(null, null);

        assertNotNull(response);
        assertTrue(response.isRequestSuccessful());
        assertNotNull(response.getResponseBody());
        assertNotNull(response.getResponseBody().getContent());
        assertTrue(response.getResponseBody().getContent().isEmpty());

        verify(
                getRequestedFor(
                        urlPathEqualTo("/api/v1/refunds")
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenRefundReferenceIsEmpty() {

        assertThrows(
                Exception.class,
                () -> refundService.getRefundStatus("")
        );

        verify(
                0,
                getRequestedFor(
                        urlPathMatching("/api/v1/refunds/.*")
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenRefundRequestIsInvalid() {

        RefundRequest request = RefundRequest.builder()
                .transactionReference("")
                .refundReference("")
                .refundAmount(null)
                .refundReason("")
                .customerNote("")
                .build();

        assertThrows(
                Exception.class,
                () -> refundService.initiateRefund(request)
        );

        verify(
                0,
                postRequestedFor(
                        urlPathEqualTo("/api/v1/refunds/initiate-refund")
                )
        );
    }
}