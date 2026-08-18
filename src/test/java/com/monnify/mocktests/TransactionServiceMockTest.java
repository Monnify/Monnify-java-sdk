package com.monnify.mocktests;
import com.google.gson.Gson;
import com.monnify.BaseMonnifyMockTest;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.transaction.*;
import com.monnify.services.transaction.TransactionService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceMockTest extends BaseMonnifyMockTest {

    private final TransactionService transactionService = new TransactionService();
    private final Gson gson = new Gson();

    @Test
    void shouldInitializeTransaction() {

        TransactionRequest request = TransactionRequest.builder()
                .amount(new BigDecimal("1000.00"))
                .customerName("John Doe")
                .customerEmail("john.doe@example.com")
                .paymentReference("PAY-001")
                .paymentDescription("Test payment")
                .currencyCode("NGN")
                .contractCode("1234567890")
                .redirectUrl("https://example.com/callback")
                .build();

        String response = "{"
                + "\"requestSuccessful\":true,"
                + "\"responseMessage\":\"success\","
                + "\"responseCode\":\"0\","
                + "\"responseBody\":{"
                + "\"transactionReference\":\"MNFY-TRANS-001\","
                + "\"paymentReference\":\"PAY-001\","
                + "\"amount\":1000.00,"
                + "\"currencyCode\":\"NGN\","
                + "\"paymentStatus\":\"PENDING\""
                + "}"
                + "}";

        stubFor(post(urlEqualTo("/api/v1/merchant/transactions/init-transaction"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        MonnifyBaseResponse<TransactionResponse> result =
                transactionService.initializeTransaction(request);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());

        verify(postRequestedFor(
                urlEqualTo("/api/v1/merchant/transactions/init-transaction")));
    }

    @Test
    void shouldPayWithBankTransfer() {

        BankTransferRequest request = BankTransferRequest.builder()
                .transactionReference("MNFY-TRANS-001")
                .bankCode("058")
                .build();

        String response = "{"
                + "\"requestSuccessful\":true,"
                + "\"responseMessage\":\"success\","
                + "\"responseCode\":\"0\","
                + "\"responseBody\":{"
                + "\"transactionReference\":\"MNFY-TRANS-001\","
                + "\"paymentReference\":\"PAY-001\","
                + "\"accountNumber\":\"1234567890\","
                + "\"accountName\":\"John Doe\","
                + "\"bankName\":\"GTBank\","
                + "\"bankCode\":\"058\""
                + "}"
                + "}";

        stubFor(post(urlEqualTo("/api/v1/merchant/bank-transfer/init-payment"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        MonnifyBaseResponse<BankTransferResponse> result =
                transactionService.payWithBankTransfer(request);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());

        verify(postRequestedFor(
                urlEqualTo("/api/v1/merchant/bank-transfer/init-payment")));
    }

    @Test
    void shouldChargeCard() {

        ChargeCardRequest.CardDetails card =
                new ChargeCardRequest.CardDetails();

        card.setNumber("4111111111111111");
        card.setExpiryMonth("12");
        card.setExpiryYear("30");
        card.setPin("1234");
        card.setCvv("123");

        ChargeCardRequest request = ChargeCardRequest.builder()
                .transactionReference("MNFY-TRANS-001")
                .collectionChannel("CARD")
                .card(card)
                .build();

        String response = "{"
                + "\"requestSuccessful\":true,"
                + "\"responseMessage\":\"success\","
                + "\"responseCode\":\"0\","
                + "\"responseBody\":{"
                + "\"authorizedAmount\":1000.00,"
                + "\"paymentReference\":\"PAY-001\","
                + "\"transactionReference\":\"MNFY-TRANS-001\","
                + "\"status\":\"SUCCESS\","
                + "\"message\":\"Card charged successfully\""
                + "}"
                + "}";

        stubFor(post(urlEqualTo("/api/v1/merchant/cards/charge"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        MonnifyBaseResponse<ChargeCardResponse> result =
                transactionService.chargeCard(request);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());

        assertEquals(
                "PAY-001",
                result.getResponseBody().getPaymentReference()
        );

        assertEquals(
                "SUCCESS",
                result.getResponseBody().getStatus()
        );

        verify(postRequestedFor(
                urlEqualTo("/api/v1/merchant/cards/charge")));
    }

    @Test
    void shouldChargeCardToken() {

        ChargeCardTokenRequest request = ChargeCardTokenRequest.builder()
                .cardToken("card-token-001")
                .amount(new BigDecimal("1000.00"))
                .customerName("John Doe")
                .customerEmail("john.doe@example.com")
                .paymentReference("PAY-002")
                .paymentDescription("Token card payment")
                .currencyCode("NGN")
                .contractCode("1234567890")
                .apiKey("test-api-key")
                .build();

        String response = "{"
                + "\"requestSuccessful\":true,"
                + "\"responseMessage\":\"success\","
                + "\"responseCode\":\"0\","
                + "\"responseBody\":{"
                + "\"transactionReference\":\"MNFY-TRANS-002\","
                + "\"paymentReference\":\"PAY-002\","
                + "\"paymentStatus\":\"PAID\","
                + "\"amountPaid\":1000.00,"
                + "\"currency\":\"NGN\""
                + "}"
                + "}";

        stubFor(post(urlEqualTo("/api/v1/merchant/cards/charge-card-token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        MonnifyBaseResponse<TransactionStatusResponse> result =
                transactionService.chargeCardToken(request);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());

        assertEquals(
                "MNFY-TRANS-002",
                result.getResponseBody().getTransactionReference()
        );

        verify(postRequestedFor(
                urlEqualTo("/api/v1/merchant/cards/charge-card-token")));
    }

    @Test
    void shouldAuthorizeCardOtp() {

        AuthorizeOtpRequest request = AuthorizeOtpRequest.builder()
                .transactionReference("MNFY-TRANS-001")
                .collectionChannel("CARD")
                .tokenId("otp-token-001")
                .token("123456")
                .build();

        String response = "{"
                + "\"requestSuccessful\":true,"
                + "\"responseMessage\":\"success\","
                + "\"responseCode\":\"0\","
                + "\"responseBody\":{"
                + "\"authorizedAmount\":1000.00,"
                + "\"paymentReference\":\"PAY-001\","
                + "\"transactionReference\":\"MNFY-TRANS-001\","
                + "\"status\":\"SUCCESS\","
                + "\"message\":\"OTP authorization successful\""
                + "}"
                + "}";

        stubFor(post(urlEqualTo("/api/v1/merchant/cards/otp/authorize"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        MonnifyBaseResponse<ChargeCardResponse> result =
                transactionService.authorizeCardOtp(request);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());

        assertEquals(
                "SUCCESS",
                result.getResponseBody().getStatus()
        );

        verify(postRequestedFor(
                urlEqualTo("/api/v1/merchant/cards/otp/authorize")));
    }

    @Test
    void shouldSearchTransactions() {

        String response = "{"
                + "\"requestSuccessful\":true,"
                + "\"responseMessage\":\"success\","
                + "\"responseCode\":\"0\","
                + "\"responseBody\":{"
                + "\"content\":["
                + "{"
                + "\"transactionReference\":\"MNFY-TRANS-001\","
                + "\"paymentReference\":\"PAY-001\","
                + "\"amountPaid\":1000.00,"
                + "\"paymentStatus\":\"PAID\""
                + "}"
                + "],"
                + "\"pageable\":{"
                + "\"pageNumber\":0,"
                + "\"pageSize\":10"
                + "},"
                + "\"totalElements\":1,"
                + "\"totalPages\":1"
                + "}"
                + "}";

        stubFor(get(urlPathEqualTo("/api/v1/transactions/search"))
                .withQueryParam("pageNo", equalTo("0"))
                .withQueryParam("pageSize", equalTo("10"))
                .withQueryParam("paymentReference", equalTo("PAY-001"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        MonnifyBaseResponse<SearchResponse<TransactionRecord>> result =
                transactionService.searchTransactions(
                        0,
                        10,
                        "PAY-001",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        "PAID",
                        null,
                        null
                );

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());

        verify(getRequestedFor(
                urlPathEqualTo("/api/v1/transactions/search"))
                .withQueryParam("pageNo", equalTo("0"))
                .withQueryParam("pageSize", equalTo("10"))
                .withQueryParam("paymentReference", equalTo("PAY-001")));
    }

    @Test
    void shouldGetTransactionStatus() {

        String response = "{"
                + "\"requestSuccessful\":true,"
                + "\"responseMessage\":\"success\","
                + "\"responseCode\":\"0\","
                + "\"responseBody\":{"
                + "\"transactionReference\":\"MNFY-TRANS-001\","
                + "\"paymentReference\":\"PAY-001\","
                + "\"amountPaid\":1000.00,"
                + "\"totalPayable\":1000.00,"
                + "\"paymentStatus\":\"PAID\","
                + "\"currency\":\"NGN\""
                + "}"
                + "}";

        stubFor(get(urlEqualTo("/api/v2/transactions/MNFY-TRANS-001"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        MonnifyBaseResponse<TransactionStatusResponse> result =
                transactionService.getStatus("MNFY-TRANS-001");

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());

        assertEquals(
                "MNFY-TRANS-001",
                result.getResponseBody().getTransactionReference()
        );

        assertEquals(
                "PAID",
                result.getResponseBody().getPaymentStatus()
        );

        verify(getRequestedFor(
                urlEqualTo("/api/v2/transactions/MNFY-TRANS-001")));
    }

    @Test
    void shouldGetStatusByPaymentReference() {

        String response = "{"
                + "\"requestSuccessful\":true,"
                + "\"responseMessage\":\"success\","
                + "\"responseCode\":\"0\","
                + "\"responseBody\":{"
                + "\"transactionReference\":\"MNFY-TRANS-001\","
                + "\"paymentReference\":\"PAY-001\","
                + "\"paymentStatus\":\"PAID\","
                + "\"amountPaid\":1000.00,"
                + "\"currency\":\"NGN\""
                + "}"
                + "}";

        stubFor(get(urlPathEqualTo("/api/v2/merchant/transactions/query"))
                .withQueryParam("paymentReference", equalTo("PAY-001"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        MonnifyBaseResponse<TransactionStatusResponse> result =
                transactionService.getStatusByReference(
                        "PAY-001",
                        null
                );

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());

        assertEquals(
                "PAY-001",
                result.getResponseBody().getPaymentReference()
        );

        assertEquals(
                "PAID",
                result.getResponseBody().getPaymentStatus()
        );

        verify(getRequestedFor(
                urlPathEqualTo("/api/v2/merchant/transactions/query"))
                .withQueryParam(
                        "paymentReference",
                        equalTo("PAY-001")
                ));
    }

    @Test
    void shouldGetStatusByTransactionReference() {

        String response = "{"
                + "\"requestSuccessful\":true,"
                + "\"responseMessage\":\"success\","
                + "\"responseCode\":\"0\","
                + "\"responseBody\":{"
                + "\"transactionReference\":\"MNFY-TRANS-001\","
                + "\"paymentReference\":\"PAY-001\","
                + "\"paymentStatus\":\"PAID\","
                + "\"amountPaid\":1000.00"
                + "}"
                + "}";

        stubFor(get(urlPathEqualTo("/api/v2/merchant/transactions/query"))
                .withQueryParam(
                        "transactionReference",
                        equalTo("MNFY-TRANS-001")
                )
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        MonnifyBaseResponse<TransactionStatusResponse> result =
                transactionService.getStatusByReference(
                        null,
                        "MNFY-TRANS-001"
                );

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());

        assertEquals(
                "MNFY-TRANS-001",
                result.getResponseBody().getTransactionReference()
        );

        verify(getRequestedFor(
                urlPathEqualTo("/api/v2/merchant/transactions/query"))
                .withQueryParam(
                        "transactionReference",
                        equalTo("MNFY-TRANS-001")
                ));
    }

    @Test
    void shouldThrowExceptionWhenTransactionReferenceIsEmpty() {

        assertThrows(
                MonnifyValidationException.class,
                () -> transactionService.getStatus("")
        );
    }

    @Test
    void shouldThrowExceptionWhenBothReferencesAreEmpty() {

        assertThrows(
                MonnifyValidationException.class,
                () -> transactionService.getStatusByReference(
                        null,
                        null
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenBothReferencesAreProvided() {

        assertThrows(
                MonnifyValidationException.class,
                () -> transactionService.getStatusByReference(
                        "PAY-001",
                        "MNFY-TRANS-001"
                )
        );
    }
}