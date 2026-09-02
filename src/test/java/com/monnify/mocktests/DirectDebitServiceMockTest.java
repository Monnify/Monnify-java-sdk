package com.monnify.mocktests;

import com.monnify.BaseMonnifyMockTest;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.directdebit.*;
import com.monnify.services.directdebit.DirectDebitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class DirectDebitServiceMockTest extends BaseMonnifyMockTest {

    private DirectDebitService directDebitService;

    @BeforeEach
    void init() {
        directDebitService = new DirectDebitService();
    }
    private MandateRequest createValidMandateRequest() {
        return MandateRequest.builder()
                .contractCode("CONTRACT123")
                .mandateReference("MANDATE-REF-123")
                .autoRenew(true)
                .customerCancellation(true)
                .customerName("Halima Ismail")
                .customerPhoneNumber("08012345678")
                .customerEmailAddress("halima@example.com")
                .customerAddress("Lagos, Nigeria")
                .customerAccountName("Halima Ismail")
                .customerAccountNumber("1234567890")
                .customerAccountBankCode("044")
                .mandateDescription("Monthly subscription payment")
                .mandateStartDate("2026-08-13T00:00:00")
                .mandateEndDate("2027-08-13T00:00:00")
                .mandateAmount(new BigDecimal("10000.00"))
                .debitAmount(new BigDecimal("1000.00"))
                .build();
    }

    @Test
    void shouldCreateMandateSuccessfully() {

        stubFor(post(urlPathEqualTo("/api/v1/direct-debit/mandate/create"))
                        .withHeader("Authorization", equalTo("Bearer mock-access-token"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\":true,"
                                                        + "\"responseMessage\":\"success\","
                                                        + "\"responseCode\":\"0\","
                                                        + "\"responseBody\":{"
                                                        + "\"mandateCode\":\"MANDATE123\","
                                                        + "\"status\":\"PENDING_AUTHORIZATION\""
                                                        + "}"
                                                        + "}"
                                        )));

        MandateRequest request = createValidMandateRequest();
        MonnifyBaseResponse<MandateResponse> result = directDebitService.createMandate(request);

        assertNotNull(result);
        assertEquals("0", result.getResponseCode());
        assertTrue(result.isRequestSuccessful());

        verify(1, postRequestedFor(urlPathEqualTo("/api/v1/direct-debit/mandate/create"
        )));
    }


    @Test
    void shouldUsePostForCreateMandate() {

        stubFor(post(urlPathEqualTo("/api/v1/direct-debit/mandate/create"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader(
                                                "Content-Type",
                                                "application/json"
                                        )
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\":true,"
                                                        + "\"responseMessage\":\"success\","
                                                        + "\"responseCode\":\"0\","
                                                        + "\"responseBody\":{}"
                                                        + "}"
                                        )
                        )
        );


        MandateRequest request = createValidMandateRequest();


        directDebitService.createMandate(request);

        verify(1, postRequestedFor(urlPathEqualTo("/api/v1/direct-debit/mandate/create")));
        verify(0, getRequestedFor(urlPathEqualTo("/api/v1/direct-debit/mandate/create")));
    }

    @Test
    void shouldGetMandateSuccessfully() {

        stubFor(
                get(urlPathEqualTo(
                        "/api/v1/direct-debit/mandate/"
                ))
                        .withQueryParam(
                                "mandateReferences",
                                equalTo("MANDATE123")
                        )
                        .withHeader(
                                "Authorization",
                                equalTo("Bearer mock-access-token")
                        )
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader(
                                                "Content-Type",
                                                "application/json"
                                        )
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\":true,"
                                                        + "\"responseMessage\":\"success\","
                                                        + "\"responseCode\":\"0\","
                                                        + "\"responseBody\":["
                                                        + "{"
                                                        + "\"mandateCode\":\"MANDATE123\","
                                                        + "\"status\":\"ACTIVE\""
                                                        + "}"
                                                        + "]"
                                                        + "}"
                                        )
                        )
        );

        MonnifyBaseResponse<List<MandateStatusResponse>> result = directDebitService.getMandate("MANDATE123");

        assertNotNull(result);
        assertEquals("0", result.getResponseCode());
        assertTrue(result.isRequestSuccessful());

        assertNotNull(result.getResponseBody());
        assertEquals(1, result.getResponseBody().size());

        verify(1, getRequestedFor(urlPathEqualTo("/api/v1/direct-debit/mandate/"))
                        .withQueryParam("mandateReferences", equalTo("MANDATE123")));
    }

    @Test
    void shouldRejectEmptyMandateReference() {
        assertThrows(MonnifyValidationException.class, () -> directDebitService.getMandate(""));
        verify(0, getRequestedFor(urlPathEqualTo("/api/v1/direct-debit/mandate/")));
    }

    @Test
    void shouldRejectNullMandateReference() {
        assertThrows(MonnifyValidationException.class, () -> directDebitService.getMandate(null));
    }
    @Test
    void shouldDebitMandateSuccessfully() {

        /*
         * Mock the debit mandate endpoint.
         */
        stubFor(
                post(urlPathEqualTo("/api/v1/direct-debit/mandate/debit"))
                        .withHeader("Authorization", equalTo("Bearer mock-access-token"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\":true,"
                                                        + "\"responseMessage\":\"success\","
                                                        + "\"responseCode\":\"0\","
                                                        + "\"responseBody\":{"
                                                        + "\"transactionStatus\":\"SUCCESS\","
                                                        + "\"responseMessage\":\"Debit successful\","
                                                        + "\"transactionReference\":\"TXN123456\","
                                                        + "\"paymentReference\":\"PAY123\","
                                                        + "\"debitAmount\":1000.00,"
                                                        + "\"narration\":\"Payment for subscription\","
                                                        + "\"mandateCode\":\"MANDATE123\""
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );
        MandateDebitRequest request = new MandateDebitRequest();
        request.setPaymentReference("PAY123");
        request.setMandateCode("MANDATE123");
        request.setDebitAmount(new BigDecimal("1000.00"));
        request.setNarration("Payment for subscription");
        request.setCustomerEmail("customer@example.com");
        MonnifyBaseResponse<MandateDebitResponse> result = directDebitService.debitMandate(request);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertEquals("success", result.getResponseMessage());

        assertNotNull(result.getResponseBody());

        MandateDebitResponse response = result.getResponseBody();

        assertEquals("SUCCESS", response.getTransactionStatus());
        assertEquals("Debit successful", response.getResponseMessage());
        assertEquals("TXN123456", response.getTransactionReference());
        assertEquals("PAY123", response.getPaymentReference());
        assertEquals(new BigDecimal("1000.00"), response.getDebitAmount());
        assertEquals("Payment for subscription", response.getNarration());
        assertEquals("MANDATE123", response.getMandateCode());
        verify(1, postRequestedFor(urlPathEqualTo("/api/v1/direct-debit/mandate/debit")));
        verify(postRequestedFor(urlPathEqualTo("/api/v1/direct-debit/mandate/debit")).withHeader("Authorization", equalTo("Bearer mock-access-token")));
        verify(0, getRequestedFor(urlPathEqualTo("/api/v1/direct-debit/mandate/debit")));
    }

    @Test
    void shouldGetDebitStatusSuccessfully() {

        stubFor(
                get(urlPathEqualTo("/api/v1/direct-debit/mandate/debit-status"))
                        .withQueryParam("paymentReference", equalTo("PAY123"))
                        .withHeader("Authorization", equalTo("Bearer mock-access-token"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\":true,"
                                                        + "\"responseMessage\":\"success\","
                                                        + "\"responseCode\":\"0\","
                                                        + "\"responseBody\":{"
                                                        + "\"transactionStatus\":\"SUCCESS\","
                                                        + "\"responseMessage\":\"Debit successful\","
                                                        + "\"transactionReference\":\"TXN123456\","
                                                        + "\"paymentReference\":\"PAY123\","
                                                        + "\"debitAmount\":1000.00,"
                                                        + "\"narration\":\"Payment for subscription\","
                                                        + "\"mandateCode\":\"MANDATE123\""
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        MonnifyBaseResponse<MandateDebitResponse> result = directDebitService.getDebitStatus("PAY123");

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertEquals("success", result.getResponseMessage());
        assertNotNull(result.getResponseBody());

        MandateDebitResponse response = result.getResponseBody();

        assertEquals("SUCCESS", response.getTransactionStatus());
        assertEquals("Debit successful", response.getResponseMessage());
        assertEquals("TXN123456", response.getTransactionReference());
        assertEquals("PAY123", response.getPaymentReference());
        assertEquals(new BigDecimal("1000.00"), response.getDebitAmount());
        assertEquals("Payment for subscription", response.getNarration());
        assertEquals("MANDATE123", response.getMandateCode());

        verify(
                1,
                getRequestedFor(urlPathEqualTo("/api/v1/direct-debit/mandate/debit-status"))
                        .withQueryParam("paymentReference", equalTo("PAY123"))
        );

        verify(
                getRequestedFor(urlPathEqualTo("/api/v1/direct-debit/mandate/debit-status"))
                        .withQueryParam("paymentReference", equalTo("PAY123"))
                        .withHeader("Authorization", equalTo("Bearer mock-access-token"))
        );
    }
    @Test
    void shouldEncodePaymentReference() {

        String paymentReference = "PAY 123";

        stubFor(
                get(urlPathEqualTo("/api/v1/direct-debit/mandate/debit-status"))
                        .withQueryParam("paymentReference", equalTo("PAY 123"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\":true,"
                                                        + "\"responseMessage\":\"success\","
                                                        + "\"responseCode\":\"0\","
                                                        + "\"responseBody\":{"
                                                        + "\"paymentReference\":\"PAY 123\""
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        MonnifyBaseResponse<MandateDebitResponse> result =
                directDebitService.getDebitStatus(paymentReference);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());

        verify(
                1,
                getRequestedFor(urlPathEqualTo("/api/v1/direct-debit/mandate/debit-status"))
                        .withQueryParam("paymentReference", equalTo("PAY 123"))
        );
    }

    @Test
    void shouldRejectEmptyPaymentReference() {

        assertThrows(
                MonnifyValidationException.class,
                () -> directDebitService.getDebitStatus("")
        );

        verify(
                0,
                getRequestedFor(urlPathEqualTo("/api/v1/direct-debit/mandate/debit-status"))
        );
    }

    @Test
    void shouldRejectNullPaymentReference() {

        assertThrows(
                MonnifyValidationException.class,
                () -> directDebitService.getDebitStatus(null)
        );

        verify(
                0,
                getRequestedFor(urlPathEqualTo("/api/v1/direct-debit/mandate/debit-status"))
        );
    }

// ============================================================
// CANCEL MANDATE
// ============================================================

    @Test
    void shouldCancelMandateSuccessfully() {

        stubFor(
                patch(urlPathEqualTo("/api/v1/direct-debit/mandate/cancel-mandate/MANDATE123"))
                        .withHeader("Authorization", equalTo("Bearer mock-access-token"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\":true,"
                                                        + "\"responseMessage\":\"success\","
                                                        + "\"responseCode\":\"0\","
                                                        + "\"responseBody\":{"
                                                        + "\"mandateCode\":\"MANDATE123\""
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        MonnifyBaseResponse<MandateResponse> result =
                directDebitService.cancelMandate("MANDATE123");

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertEquals("success", result.getResponseMessage());

        verify(
                1,
                patchRequestedFor(
                        urlPathEqualTo("/api/v1/direct-debit/mandate/cancel-mandate/MANDATE123")
                )
        );

        verify(
                patchRequestedFor(
                        urlPathEqualTo("/api/v1/direct-debit/mandate/cancel-mandate/MANDATE123")
                )
                        .withHeader("Authorization", equalTo("Bearer mock-access-token"))
        );
    }

    @Test
    void shouldRejectEmptyMandateCode() {

        assertThrows(MonnifyValidationException.class, () -> directDebitService.cancelMandate(""));

        verify(0, patchRequestedFor(
                        urlPathMatching("/api/v1/direct-debit/mandate/cancel-mandate/.*")
                )
        );
    }

    @Test
    void shouldRejectNullMandateCode() {

        assertThrows(
                MonnifyValidationException.class,
                () -> directDebitService.cancelMandate(null)
        );

        verify(
                0,
                patchRequestedFor(
                        urlPathMatching("/api/v1/direct-debit/mandate/cancel-mandate/.*")
                )
        );
    }

    @Test
    void shouldReturnErrorResponseWhenCreateMandateFails() {

        stubFor(
                post(urlPathEqualTo("/api/v1/direct-debit/mandate/create"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\":false,"
                                                        + "\"responseMessage\":\"Invalid mandate\","
                                                        + "\"responseCode\":\"99\","
                                                        + "\"responseBody\":null"
                                                        + "}"
                                        )
                        )
        );

        MandateRequest request = new MandateRequest();

        request.setContractCode("CONTRACT123");
        request.setMandateReference("MANDATE-REF-123");
        request.setCustomerName("Test Customer");
        request.setCustomerPhoneNumber("08012345678");
        request.setCustomerEmailAddress("customer@example.com");
        request.setCustomerAddress("123 Test Street");
        request.setCustomerAccountName("Test Customer");
        request.setCustomerAccountNumber("0123456789");
        request.setCustomerAccountBankCode("058");
        request.setMandateDescription("Subscription payment");
        request.setMandateStartDate("2026-08-13T00:00:00");
        request.setMandateEndDate("2027-08-13T00:00:00");
        request.setMandateAmount(new BigDecimal("1000.00"));
        request.setDebitAmount(new BigDecimal("1000.00"));
        request.setAutoRenew(false);
        request.setCustomerCancellation(false);

        MonnifyBaseResponse<MandateResponse> result =
                directDebitService.createMandate(request);

        assertNotNull(result);
        assertFalse(result.isRequestSuccessful());
        assertEquals("99", result.getResponseCode());
        assertEquals("Invalid mandate", result.getResponseMessage());

        verify(
                1,
                postRequestedFor(urlPathEqualTo("/api/v1/direct-debit/mandate/create"))
        );
    }
}