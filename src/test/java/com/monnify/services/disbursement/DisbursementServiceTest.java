package com.monnify.services.disbursement;

import com.monnify.Monnify;
import com.monnify.models.disbursement.BatchDisbursementRequest;
import com.monnify.models.disbursement.DisbursementTransaction;
import com.monnify.models.disbursement.OnValidationFailure;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DisbursementServiceTest {
    private final DisbursementService disbursementService = new DisbursementService();
    @BeforeAll
    static void setUp() {
        assertNotNull(System.getenv("apiKey"));
        assertNotNull(System.getenv("secretKey"));
        assertNotNull(System.getenv("contractCode"));
        Monnify.initialize(System.getenv("apiKey"), System.getenv("secretKey"));
    }

    @Test
    void disburseSingle() {
    }

    @Test
    void disburseBatch() {
        disbursementService.disburseBatch(BatchDisbursementRequest.builder()
                .batchReference(UUID.randomUUID().toString())
                .title("Sdk Test batch")
                .narration("test batch on sdk")
                .notificationInterval(25)
                .onValidationFailure(OnValidationFailure.CONTINUE)
                .sourceAccountNumber("7853297429")
                .transactionList(Arrays.asList(DisbursementTransaction.builder()
                        .amount(BigDecimal.valueOf(1000))
                        .currency("NGN")
                        .narration("batch dis 1")
                        .reference(UUID.randomUUID().toString())
                        .destinationAccountNumber("0104430292")
                        .destinationBankCode("044")
                        .build()
//                        ,DisbursementTransaction.builder()
//                        .amount(BigDecimal.valueOf(50))
//                        .currency("NGN")
//                        .narration("batch dis 2")
//                        .reference(UUID.randomUUID().toString())
//                        .destinationAccountNumber("0104430292")
//                        .destinationBankCode("044")
//                        .build(),DisbursementTransaction.builder()
//                        .amount(BigDecimal.valueOf(50))
//                        .currency("NGN")
//                        .narration("batch dis 3")
//                        .reference(UUID.randomUUID().toString())
//                        .destinationAccountNumber("0104430292")
//                        .destinationBankCode("044")
//                        .build()
                )).build());
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
    void getDisbursementSummary() {
    }

    @Test
    void getAllSingleDisbursements() {
    }

    @Test
    void getAllBulkDisbursements() {
    }

    @Test
    void getBulkTransferTransactions() {
    }

    @Test
    void searchTransactions() {
    }
}