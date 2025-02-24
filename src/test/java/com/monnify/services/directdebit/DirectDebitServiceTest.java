package com.monnify.services.directdebit;

import com.google.gson.Gson;
import com.monnify.Monnify;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.directdebit.MandateRequest;
import com.monnify.models.directdebit.MandateResponse;
import com.monnify.models.directdebit.MandateStatusResponse;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static com.monnify.MainTest.assertSuccess;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DirectDebitServiceTest {
    private final DirectDebitService directDebitService = new DirectDebitService();

    @BeforeAll
    static void setUp() {
        assertNotNull(System.getenv("apiKey"));
        assertNotNull(System.getenv("secretKey"));
        assertNotNull(System.getenv("contractCode"));
        Monnify.initialize(System.getenv("apiKey"), System.getenv("secretKey"));
    }
    private static final String reference = UUID.randomUUID().toString();
    private static String mandateCode = "";
    MandateRequest request = MandateRequest.builder()
            .contractCode(System.getenv("contractCode"))
            .mandateReference(reference)
            .autoRenew(true)
            .customerCancellation(false)
            .customerName("John Doe")
            .customerPhoneNumber("08012345678")
            .customerEmailAddress("johndoe@example.com")
            .customerAddress("123 Street, City, Country")
            .customerAccountName("John Doe Account")
            .customerAccountNumber("1234567890")
            .customerAccountBankCode("044")
            .mandateDescription("Monthly subscription")
            .mandateStartDate(LocalDateTime.of(2025, 3, 11, 8, 55, 0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
            .mandateEndDate(LocalDateTime.of(2026, 3, 11, 8, 55, 0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
            .mandateAmount(new BigDecimal("5000.00"))
            .debitAmount(new BigDecimal("5000.00"))
            .build();

    @Test
    @Order(1)
    void createMandate() {
        MonnifyBaseResponse<MandateResponse> response = directDebitService.createMandate(request);
        assertSuccess(response);
        mandateCode = response.getResponseBody().getMandateCode();
    }

    @Test
    @Order(2)
    void getMandate() {
        MonnifyBaseResponse<List<MandateStatusResponse>> response = directDebitService.getMandate(reference);
        assertSuccess(response);
    }

    @Test
    void debitMandate() {
    }

    @Test
    void getDebitStatus() {
    }

    @Test
    @Order(3)
    void cancelMandate() throws InterruptedException {
        Thread.sleep(3000);
        MonnifyBaseResponse<MandateResponse> response = directDebitService.cancelMandate(mandateCode);
        assertSuccess(response);
    }
}