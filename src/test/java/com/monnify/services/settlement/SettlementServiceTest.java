package com.monnify.services.settlement;

import com.google.gson.Gson;
import com.monnify.Monnify;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.settlement.SettlementResponse;
import com.monnify.models.transaction.TransactionStatusResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.monnify.MainTest.assertSuccess;
import static org.junit.jupiter.api.Assertions.*;

class SettlementServiceTest {
    private final SettlementService settlementService = new SettlementService();
    @BeforeAll
    static void setUp() {
        assertNotNull(System.getenv("apiKey"));
        assertNotNull(System.getenv("secretKey"));
        assertNotNull(System.getenv("contractCode"));
        Monnify.initialize(System.getenv("apiKey"), System.getenv("secretKey"));
    }

    @Test
    void getTransactionsBySettlementReference() {
        MonnifyBaseResponse<SearchResponse<TransactionStatusResponse>> response = settlementService.getTransactionsBySettlementReference("M19292", 0, 10);
        assertSuccess(response);
    }

    @Test
    void getSettlementInformation() {
        MonnifyBaseResponse<SettlementResponse> response = settlementService.getSettlementInformation("MNFY|35|20241111222852|000001");
        assertSuccess(response);
    }
}