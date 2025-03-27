package com.monnify.services.refund;

import com.monnify.Monnify;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.refund.RefundRequest;
import com.monnify.models.refund.RefundResponse;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.UUID;

import static com.monnify.MainTest.assertSuccess;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RefundServiceTest {
    private final RefundService refundService = new RefundService();
    @BeforeAll
    static void setUp() {
        assertNotNull(System.getenv("apiKey"));
        assertNotNull(System.getenv("secretKey"));
        assertNotNull(System.getenv("contractCode"));
        Monnify.initialize(System.getenv("apiKey"), System.getenv("secretKey"));
    }
    private static final String reference = UUID.randomUUID().toString();
    RefundRequest refundRequest = RefundRequest.builder()
            .refundReference(reference)
            .refundAmount(BigDecimal.TEN)
            .refundReason("Test refund reason")
            .customerNote("Test note")
            .destinationAccountBankCode("50515")
            .destinationAccountNumber("1234567890")
            .transactionReference("MNFY|20250221120333123|029384")
            .build();

    @Test
    @Order(1)
    void initiateRefund() {
        MonnifyBaseResponse<RefundResponse> response = refundService.initiateRefund(refundRequest);
        assertSuccess(response);
    }

    @Test
    @Order(2)
    void getRefundStatus() {
        MonnifyBaseResponse<RefundResponse> response = refundService.getRefundStatus(reference);
        assertSuccess(response);
    }

    @Test
    @Order(3)
    void getRefunds() {
        MonnifyBaseResponse<SearchResponse<RefundResponse>> response = refundService.getRefunds(0,10);
        assertSuccess(response);
        assertFalse(response.getResponseBody().isEmpty());
    }
}