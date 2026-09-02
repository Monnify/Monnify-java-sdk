package com.monnify.mocktests;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.monnify.BaseMonnifyMockTest;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.disbursement.BatchDisbursementRequest;
import com.monnify.models.disbursement.SingleDisbursementRequest;
import com.monnify.models.disbursement.SearchTransactionsRequest;
import com.monnify.models.disbursement.OnValidationFailure;
import com.monnify.models.disbursement.SingleDisbursementResponse;
import com.monnify.models.disbursement.DisbursementTransaction;
import com.monnify.models.disbursement.BatchDisbursementResponse;
import com.monnify.models.disbursement.TransferDetails;
import com.monnify.models.disbursement.DisbursementSummaryResponse;

import com.monnify.models.wallet.WalletBalanceResponse;
import com.monnify.services.disbursement.DisbursementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class DisbursementMockTest  extends BaseMonnifyMockTest {
    private DisbursementService disbursementService;

    @BeforeEach
    void setUpService() {
        disbursementService = new DisbursementService();
    }

    @Test
    void shouldDisburseSingleSuccessfully() {

        stubFor(
                post(urlPathEqualTo("/api/v2/disbursements/single"))
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
                                                        + "\"amount\":1000.00,"
                                                        + "\"reference\":\"DISB123\","
                                                        + "\"status\":\"SUCCESS\","
                                                        + "\"totalFee\":10.00,"
                                                        + "\"destinationAccountName\":\"Test Customer\","
                                                        + "\"destinationBankName\":\"Test Bank\","
                                                        + "\"destinationAccountNumber\":\"0123456789\","
                                                        + "\"destinationBankCode\":\"058\""
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        SingleDisbursementRequest request = new SingleDisbursementRequest();
        request.setAmount(new BigDecimal("1000.00"));
        request.setReference("DISB123");
        request.setNarration("Test disbursement");
        request.setDestinationBankCode("058");
        request.setDestinationAccountNumber("0123456789");
        request.setCurrency("NGN");
        request.setSourceAccountNumber("1234567890");
        request.setDestinationAccountName("Test Customer");
        request.setAsync(false);

        MonnifyBaseResponse<SingleDisbursementResponse> result =
                disbursementService.disburseSingle(request);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertEquals("success", result.getResponseMessage());
        assertNotNull(result.getResponseBody());
        assertEquals(new BigDecimal("1000.00"), result.getResponseBody().getAmount());
        assertEquals("DISB123", result.getResponseBody().getReference());
        assertEquals("SUCCESS", result.getResponseBody().getStatus());
        assertEquals(new BigDecimal("10.00"), result.getResponseBody().getTotalFee());
        assertEquals("Test Customer", result.getResponseBody().getDestinationAccountName());
        assertEquals("Test Bank", result.getResponseBody().getDestinationBankName());
        assertEquals("0123456789", result.getResponseBody().getDestinationAccountNumber());
        assertEquals("058", result.getResponseBody().getDestinationBankCode());

        verify(
                1,
                postRequestedFor(urlPathEqualTo("/api/v2/disbursements/single"))
        );

        verify(
                postRequestedFor(urlPathEqualTo("/api/v2/disbursements/single"))
                        .withHeader("Authorization", equalTo("Bearer mock-access-token"))
        );

        verify(
                0,
                getRequestedFor(urlPathEqualTo("/api/v2/disbursements/single"))
        );
    }

    @Test
    void shouldRejectInvalidSingleDisbursementRequest() {

        SingleDisbursementRequest request = new SingleDisbursementRequest();

        assertThrows(
                Exception.class,
                () -> disbursementService.disburseSingle(request)
        );

        verify(
                0,
                postRequestedFor(urlPathEqualTo("/api/v2/disbursements/single"))
        );
    }

    @Test
    void shouldReturnErrorResponseWhenSingleDisbursementFails() {

        stubFor(
                post(urlPathEqualTo("/api/v2/disbursements/single"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\":false,"
                                                        + "\"responseMessage\":\"Insufficient balance\","
                                                        + "\"responseCode\":\"99\","
                                                        + "\"responseBody\":null"
                                                        + "}"
                                        )
                        )
        );

        SingleDisbursementRequest request = new SingleDisbursementRequest();
        request.setAmount(new BigDecimal("1000.00"));
        request.setReference("DISB123");
        request.setNarration("Test disbursement");
        request.setDestinationBankCode("058");
        request.setDestinationAccountNumber("0123456789");
        request.setCurrency("NGN");
        request.setSourceAccountNumber("1234567890");
        request.setDestinationAccountName("Test Customer");
        request.setAsync(false);

        MonnifyBaseResponse<SingleDisbursementResponse> result =
                disbursementService.disburseSingle(request);

        assertNotNull(result);
        assertFalse(result.isRequestSuccessful());
        assertEquals("99", result.getResponseCode());
        assertEquals("Insufficient balance", result.getResponseMessage());

        verify(
                1,
                postRequestedFor(urlPathEqualTo("/api/v2/disbursements/single"))
        );
    }

    @Test
    void shouldDisburseBatchSuccessfully() {

        stubFor(
                post(urlPathEqualTo("/api/v2/disbursements/batch"))
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
                                                        + "\"totalAmount\":2000.00,"
                                                        + "\"totalFee\":20.00,"
                                                        + "\"batchReference\":\"BATCH123\","
                                                        + "\"batchStatus\":\"SUCCESS\","
                                                        + "\"totalTransactionsCount\":2"
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        DisbursementTransaction transaction1 = new DisbursementTransaction();
        transaction1.setAmount(new BigDecimal("1000.00"));
        transaction1.setReference("TXN001");
        transaction1.setNarration("Payment one");
        transaction1.setDestinationBankCode("058");
        transaction1.setDestinationAccountNumber("0123456789");
        transaction1.setCurrency("NGN");

        DisbursementTransaction transaction2 = new DisbursementTransaction();
        transaction2.setAmount(new BigDecimal("1000.00"));
        transaction2.setReference("TXN002");
        transaction2.setNarration("Payment two");
        transaction2.setDestinationBankCode("058");
        transaction2.setDestinationAccountNumber("9876543210");
        transaction2.setCurrency("NGN");

        BatchDisbursementRequest request = new BatchDisbursementRequest();
        request.setTitle("Test Batch");
        request.setBatchReference("BATCH123");
        request.setNarration("Batch disbursement");
        request.setSourceAccountNumber("1234567890");
        request.setOnValidationFailure(OnValidationFailure.values()[0]);
        request.setTransactionList(java.util.Arrays.asList(transaction1, transaction2));

        MonnifyBaseResponse<BatchDisbursementResponse> result =
                disbursementService.disburseBatch(request);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertEquals("success", result.getResponseMessage());
        assertNotNull(result.getResponseBody());
        assertEquals(new BigDecimal("2000.00"), result.getResponseBody().getTotalAmount());
        assertEquals(new BigDecimal("20.00"), result.getResponseBody().getTotalFee());
        assertEquals("BATCH123", result.getResponseBody().getBatchReference());
        assertEquals("SUCCESS", result.getResponseBody().getBatchStatus());
        assertEquals(2, result.getResponseBody().getTotalTransactionsCount());

        verify(
                1,
                postRequestedFor(urlPathEqualTo("/api/v2/disbursements/batch"))
        );

        verify(
                postRequestedFor(urlPathEqualTo("/api/v2/disbursements/batch"))
                        .withHeader("Authorization", equalTo("Bearer mock-access-token"))
        );
    }

    @Test
    void shouldRejectInvalidBatchDisbursementRequest() {

        BatchDisbursementRequest request = new BatchDisbursementRequest();

        assertThrows(
                Exception.class,
                () -> disbursementService.disburseBatch(request)
        );

        verify(
                0,
                postRequestedFor(urlPathEqualTo("/api/v2/disbursements/batch"))
        );
    }

    @Test
    void shouldGetDisbursementSummarySuccessfully() {

        stubFor(
                get(urlPathEqualTo("/api/v2/disbursements/single/summary"))
                        .withQueryParam("reference", equalTo("DISB123"))
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
                                                        + "\"responseBody\":{}"
                                                        + "}"
                                        )
                        )
        );

        MonnifyBaseResponse<DisbursementSummaryResponse> result =
                disbursementService.getDisbursementSummary("DISB123");

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertEquals("success", result.getResponseMessage());

        verify(
                1,
                getRequestedFor(urlPathEqualTo("/api/v2/disbursements/single/summary"))
                        .withQueryParam("reference", equalTo("DISB123"))
        );
    }

    @Test
    void shouldRejectEmptyDisbursementReference() {

        assertThrows(
                MonnifyValidationException.class,
                () -> disbursementService.getDisbursementSummary("")
        );

        verify(
                0,
                getRequestedFor(urlPathEqualTo("/api/v2/disbursements/single/summary"))
        );
    }

    @Test
    void shouldRejectNullDisbursementReference() {

        assertThrows(
                MonnifyValidationException.class,
                () -> disbursementService.getDisbursementSummary(null)
        );

        verify(
                0,
                getRequestedFor(urlPathEqualTo("/api/v2/disbursements/single/summary"))
        );
    }

    @Test
    void shouldGetAllSingleDisbursementsSuccessfully() {

        stubFor(
                get(urlPathEqualTo("/api/v2/disbursements/single/transactions"))
                        .withQueryParam("pageNo", equalTo("0"))
                        .withQueryParam("pageSize", equalTo("10"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
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

        MonnifyBaseResponse<SearchResponse<DisbursementSummaryResponse>> result =
                disbursementService.getAllSingleDisbursements(10, 0);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());

        verify(
                1,
                getRequestedFor(urlPathEqualTo("/api/v2/disbursements/single/transactions"))
                        .withQueryParam("pageNo", equalTo("0"))
                        .withQueryParam("pageSize", equalTo("10"))
        );
    }

    @Test
    void shouldGetAllBulkDisbursementsSuccessfully() {

        stubFor(
                get(urlPathEqualTo("/api/v2/disbursements/bulk/transactions"))
                        .withQueryParam("pageNo", equalTo("0"))
                        .withQueryParam("pageSize", equalTo("10"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
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

        MonnifyBaseResponse<SearchResponse<BatchDisbursementResponse>> result =
                disbursementService.getAllBulkDisbursements(10, 0);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());

        verify(
                1,
                getRequestedFor(urlPathEqualTo("/api/v2/disbursements/bulk/transactions"))
                        .withQueryParam("pageNo", equalTo("0"))
                        .withQueryParam("pageSize", equalTo("10"))
        );
    }

    @Test
    void shouldGetBulkTransferTransactionsSuccessfully() {

        stubFor(
                get(urlPathEqualTo("/api/v2/disbursements/bulk/BATCH123/transactions"))
                        .withQueryParam("pageNo", equalTo("0"))
                        .withQueryParam("pageSize", equalTo("10"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
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

        MonnifyBaseResponse<SearchResponse<DisbursementSummaryResponse>> result =
                disbursementService.getBulkTransferTransactions("BATCH123", 10, 0);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());

        verify(
                1,
                getRequestedFor(urlPathEqualTo("/api/v2/disbursements/bulk/BATCH123/transactions"))
                        .withQueryParam("pageNo", equalTo("0"))
                        .withQueryParam("pageSize", equalTo("10"))
        );
    }

    @Test
    void shouldRejectEmptyBatchReferenceForBulkTransactions() {

        assertThrows(
                MonnifyValidationException.class,
                () -> disbursementService.getBulkTransferTransactions("", 10, 0)
        );

        verify(
                0,
                getRequestedFor(urlPathMatching("/api/v2/disbursements/bulk/.*/transactions"))
        );
    }

    @Test
    void shouldRejectNullBatchReferenceForBulkTransactions() {

        assertThrows(
                MonnifyValidationException.class,
                () -> disbursementService.getBulkTransferTransactions(null, 10, 0)
        );

        verify(
                0,
                getRequestedFor(urlPathMatching("/api/v2/disbursements/bulk/.*/transactions"))
        );
    }

    @Test
    void shouldEncodeBatchReference() {

        String batchReference = "BATCH 123";

        stubFor(
                get(urlPathMatching("/api/v2/disbursements/bulk/BATCH\\+123/transactions"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
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

        MonnifyBaseResponse<SearchResponse<DisbursementSummaryResponse>> result =
                disbursementService.getBulkTransferTransactions(batchReference, null, null);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());

        verify(
                1,
                getRequestedFor(urlPathMatching("/api/v2/disbursements/bulk/.*/transactions"))
        );
    }

    @Test
    void shouldGetBulkTransferStatusSuccessfully() {

        stubFor(
                get(urlPathEqualTo("/api/v2/disbursements/batch/summary"))
                        .withQueryParam("reference", equalTo("BATCH123"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
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

        MonnifyBaseResponse<?> result =
                disbursementService.getBulkTransferStatus("BATCH123");

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());

        verify(
                1,
                getRequestedFor(urlPathEqualTo("/api/v2/disbursements/batch/summary"))
                        .withQueryParam("reference", equalTo("BATCH123"))
        );
    }

    @Test
    void shouldRejectEmptyBatchReferenceForBulkStatus() {

        assertThrows(
                MonnifyValidationException.class,
                () -> disbursementService.getBulkTransferStatus("")
        );

        verify(
                0,
                getRequestedFor(urlPathEqualTo("/api/v2/disbursements/batch/summary"))
        );
    }

    @Test
    void shouldRejectNullBatchReferenceForBulkStatus() {

        assertThrows(
                MonnifyValidationException.class,
                () -> disbursementService.getBulkTransferStatus(null)
        );

        verify(
                0,
                getRequestedFor(urlPathEqualTo("/api/v2/disbursements/batch/summary"))
        );
    }

    @Test
    void shouldSearchTransactionsSuccessfully() {

        stubFor(
                get(urlPathEqualTo("/api/v2/disbursements/search-transactions"))
                        .withQueryParam("sourceAccountNumber", equalTo("1234567890"))
                        .withQueryParam("pageSize", equalTo("10"))
                        .withQueryParam("pageNo", equalTo("0"))
                        .withQueryParam("startDate", equalTo("2026-08-01"))
                        .withQueryParam("endDate", equalTo("2026-08-13"))
                        .withQueryParam("amountFrom", equalTo("100"))
                        .withQueryParam("amountTo", equalTo("10000"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
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

        SearchTransactionsRequest request = new SearchTransactionsRequest();
        request.setSourceAccountNumber("1234567890");
        request.setPageSize(10);
        request.setPageNo(0);
        request.setStartDate("2026-08-01");
        request.setEndDate("2026-08-13");
        request.setAmountFrom("100");
        request.setAmountTo("10000");

        MonnifyBaseResponse<SearchResponse<TransferDetails>> result = disbursementService.searchTransactions(request);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());

        verify(
                1,
                getRequestedFor(urlPathEqualTo("/api/v2/disbursements/search-transactions"))
                        .withQueryParam("sourceAccountNumber", equalTo("1234567890"))
                        .withQueryParam("pageSize", equalTo("10"))
                        .withQueryParam("pageNo", equalTo("0"))
                        .withQueryParam("startDate", equalTo("2026-08-01"))
                        .withQueryParam("endDate", equalTo("2026-08-13"))
                        .withQueryParam("amountFrom", equalTo("100"))
                        .withQueryParam("amountTo", equalTo("10000"))
        );
    }

    @Test
    void shouldSearchTransactionsWithoutOptionalParameters() {

        stubFor(
                get(urlPathEqualTo("/api/v2/disbursements/search-transactions"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
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

        SearchTransactionsRequest request = new SearchTransactionsRequest();

        MonnifyBaseResponse<SearchResponse<TransferDetails>> result =
                disbursementService.searchTransactions(request);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());

        verify(
                1,
                getRequestedFor(urlPathEqualTo("/api/v2/disbursements/search-transactions"))
        );
    }

    @Test
    void shouldGetWalletBalanceSuccessfully() {

        stubFor(
                get(urlPathEqualTo("/api/v2/disbursements/wallet-balance"))
                        .withQueryParam("accountNumber", equalTo("1234567890"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
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

        MonnifyBaseResponse<WalletBalanceResponse> result =
                disbursementService.getWalletBalance("1234567890");

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());

        verify(
                1,
                getRequestedFor(urlPathEqualTo("/api/v2/disbursements/wallet-balance"))
                        .withQueryParam("accountNumber", equalTo("1234567890"))
        );
    }

    @Test
    void shouldRejectEmptyWalletAccountNumber() {

        assertThrows(
                MonnifyValidationException.class,
                () -> disbursementService.getWalletBalance("")
        );

        verify(
                0,
                getRequestedFor(urlPathEqualTo("/api/v2/disbursements/wallet-balance"))
        );
    }

    @Test
    void shouldRejectNullWalletAccountNumber() {

        assertThrows(
                MonnifyValidationException.class,
                () -> disbursementService.getWalletBalance(null)
        );

        verify(
                0,
                getRequestedFor(urlPathEqualTo("/api/v2/disbursements/wallet-balance"))
        );
    }
}
