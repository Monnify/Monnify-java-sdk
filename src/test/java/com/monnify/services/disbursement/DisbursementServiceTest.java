package com.monnify.services.disbursement;

import com.monnify.Monnify;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.disbursement.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.monnify.MainTest.assertSuccess;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DisbursementServiceTest {
    private final DisbursementService disbursementService = new DisbursementService();
    @BeforeAll
    static void setUp() {
        assertNotNull(System.getenv("apiKey"));
        assertNotNull(System.getenv("secretKey"));
        assertNotNull(System.getenv("contractCode"));
        Monnify.initialize(System.getenv("apiKey"), System.getenv("secretKey"));
    }

    private static final String reference = UUID.randomUUID().toString();
    private static final String batchReference = UUID.randomUUID().toString();

    @Test
    @Order(1)
    void disburseSingle() {
        int randomNumber = new Random().nextInt(1000);
        MonnifyBaseResponse<SingleDisbursementResponse> response = disbursementService.disburseSingle(SingleDisbursementRequest.builder()
                .amount(BigDecimal.valueOf(randomNumber))
                .sourceAccountNumber("7853297429")
                .currency("NGN")
                .destinationBankCode("044")
                .destinationAccountNumber("0104430292")
                .reference(reference)
                .narration("Test Single trnx" + randomNumber)
                .build());
        assertSuccess(response);
    }

    @Test
    @Order(1)
    void disburseBatch() {
        int randomNumber = new Random().nextInt(1000);
        MonnifyBaseResponse<BatchDisbursementResponse> response = disbursementService.disburseBatch(BatchDisbursementRequest.builder()
                .batchReference(batchReference)
                .title("Sdk Test batch" + randomNumber)
                .narration("test batch on sdk" + randomNumber)
                .notificationInterval(25)
                .onValidationFailure(OnValidationFailure.CONTINUE)
                .sourceAccountNumber("7853297429")
                .transactionList(List.of(DisbursementTransaction.builder()
                        .amount(BigDecimal.valueOf(1000))
                        .currency("NGN")
                        .narration("batch dis" + randomNumber)
                        .reference(UUID.randomUUID().toString())
                        .destinationAccountNumber("0104430292")
                        .destinationBankCode("044")
                        .build()
                        ,DisbursementTransaction.builder()
                        .amount(BigDecimal.valueOf(500))
                        .currency("NGN")
                        .narration("batch dis" + randomNumber)
                        .reference(UUID.randomUUID().toString())
                        .destinationAccountNumber("0104430292")
                        .destinationBankCode("044")
                        .build(),DisbursementTransaction.builder()
                        .amount(BigDecimal.valueOf(250))
                        .currency("NGN")
                        .narration("batch dis" + randomNumber)
                        .reference(UUID.randomUUID().toString())
                        .destinationAccountNumber("0104430292")
                        .destinationBankCode("044")
                        .build()
                )).build());
        assertSuccess(response);
    }

    @Test
    void validateDisbursementOtp() {
    }

    @Test
    void validateBatchDisbursementOtp() {
    }

    @Test
    void resendDisbursementOtp() {
    }

    @Test
    @Order(2)
    void getDisbursementSummary() {
        MonnifyBaseResponse<DisbursementSummaryResponse> response = disbursementService.getDisbursementSummary(reference);
        assertSuccess(response);
        assertEquals(reference, response.getResponseBody().getReference());
    }

    @Test
    void getAllSingleDisbursements() {
        MonnifyBaseResponse<SearchResponse<DisbursementSummaryResponse>> response = disbursementService.getAllSingleDisbursements(10, 0);
        assertSuccess(response);
        assertEquals(10, response.getResponseBody().getSize());
    }

    @Test
    void getAllBulkDisbursements() {
        MonnifyBaseResponse<SearchResponse<BatchDisbursementResponse>> response = disbursementService.getAllBulkDisbursements(10, 0);
        assertSuccess(response);
        assertEquals(10, response.getResponseBody().getSize());
    }

    @Test
    @Order(2)
    void getBulkTransferTransactions() {
        MonnifyBaseResponse<SearchResponse<DisbursementSummaryResponse>> response = disbursementService.getBulkTransferTransactions(batchReference, 10, 0);
        assertSuccess(response);
    }

    @Test
    void searchTransactions() {
        MonnifyBaseResponse<SearchResponse<TransferDetails>> response = disbursementService.searchTransactions(SearchTransactionsRequest.builder().sourceAccountNumber("7853297429").build());
        assertSuccess(response);
    }
}