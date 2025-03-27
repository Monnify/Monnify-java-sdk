package com.monnify.services.paycode;

import com.monnify.Monnify;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.paycode.PaycodeRequest;
import com.monnify.models.paycode.PaycodeResponse;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.monnify.MainTest.assertSuccess;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaycodeServiceTest {
    private final PaycodeService paycodeService = new PaycodeService();

    @BeforeAll
    static void setUp() {
        assertNotNull(System.getenv("apiKey"));
        assertNotNull(System.getenv("secretKey"));
        assertNotNull(System.getenv("contractCode"));
        Monnify.initialize(System.getenv("apiKey"), System.getenv("secretKey"));
    }
    private static final String reference = UUID.randomUUID().toString();
    PaycodeRequest paycodeRequest = PaycodeRequest.builder()
            .paycodeReference(reference)
            .amount(BigDecimal.valueOf(1000)).beneficiaryName("Ore Sho")
            .clientId(System.getenv("apiKey"))
            .expiryDate(LocalDateTime.of(2025, 3, 11, 8, 55, 0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .build();
    private static String paycode = "";

    @Test
    @Order(1)
    void createPaycode() {
        MonnifyBaseResponse<PaycodeResponse> response = paycodeService.createPaycode(paycodeRequest);
        assertSuccess(response);
        assertNotNull(response.getResponseBody().getPaycode());
        paycode = response.getResponseBody().getPaycode();
    }

    @Test
    @Order(2)
    void getPaycode() {
        MonnifyBaseResponse<PaycodeResponse> response = paycodeService.getPaycode(reference);
        assertSuccess(response);
    }

    @Test
    @Order(3)
    void getClearPaycode() {
        MonnifyBaseResponse<PaycodeResponse> response = paycodeService.getClearPaycode(reference);
        assertSuccess(response);
        assertEquals(paycode, response.getResponseBody().getPaycode());
    }

    @Test
    @Order(4)
    void fetchPaycodes() {
        MonnifyBaseResponse<SearchResponse<PaycodeResponse>> response = paycodeService.fetchPaycodes(null, null,null,
                LocalDateTime.now().minusDays(1).toEpochSecond(ZoneOffset.UTC),
                LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC));
        assertSuccess(response);
    }

    @Test
    @Order(5)
    void deletePaycode() {
        MonnifyBaseResponse<PaycodeResponse> response = paycodeService.deletePaycode(reference);
        assertSuccess(response);

        MonnifyBaseResponse<PaycodeResponse> response2 = paycodeService.getClearPaycode(reference);
        assertFalse(response2.isRequestSuccessful());
    }
}