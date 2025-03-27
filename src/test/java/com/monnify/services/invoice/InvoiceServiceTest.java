package com.monnify.services.invoice;

import com.google.gson.Gson;
import com.monnify.MainTest;
import com.monnify.Monnify;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.invoice.InvoiceRequest;
import com.monnify.models.invoice.InvoiceResponse;
import com.monnify.models.transaction.PaymentMethod;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.monnify.MainTest.assertSuccess;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InvoiceServiceTest {
    private final InvoiceService invoiceService = new InvoiceService();
    private final Gson gson = new Gson();

    @BeforeAll
    static void setUp() {
        assertNotNull(System.getenv("apiKey"));
        assertNotNull(System.getenv("secretKey"));
        assertNotNull(System.getenv("contractCode"));
        Monnify.initialize(System.getenv("apiKey"), System.getenv("secretKey"));
    }

    private static final String reference = UUID.randomUUID().toString();
    InvoiceRequest invoiceRequest = InvoiceRequest.builder()
            .amount(new BigDecimal("500.00"))
            .invoiceReference(reference)
            .description("Payment for subscription")
            .currencyCode("NGN")
            .contractCode(System.getenv("contractCode"))
            .customerEmail("customer@example.com")
            .customerName("John Doe")
            .expiryDate(LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth().plus(1), LocalDate.now().getDayOfMonth(), 0,0,0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .redirectUrl("https://example.com/payment-success")
            .paymentMethods(Arrays.asList(PaymentMethod.CARD, PaymentMethod.ACCOUNT_TRANSFER))
            .metaData((Map<String, ?>) new HashMap<>().put("orderId", "ORD12345"))
            .incomeSplitConfig(Collections.emptyList())
            .build();


    @Test
    @Order(1)
    void createInvoice() {
        MonnifyBaseResponse<InvoiceResponse> response = invoiceService.createInvoice(invoiceRequest);
        assertSuccess(response);
    }

    @Test
    @Order(2)
    void viewInvoiceDetails() {
        MonnifyBaseResponse<InvoiceResponse> response = invoiceService.viewInvoiceDetails(reference);
        assertSuccess(response);
    }

    @Test
    @Order(3)
    void getAllInvoices() {
        MonnifyBaseResponse<SearchResponse<InvoiceResponse>> response = invoiceService.getAllInvoices(0,10);
        assertSuccess(response);
    }

    @Test
    @Order(4)
    void cancelInvoice() {
        MonnifyBaseResponse<InvoiceResponse> response = invoiceService.cancelInvoice(reference);
        assertSuccess(response);

        MonnifyBaseResponse<InvoiceResponse> response2 = invoiceService.viewInvoiceDetails(reference);
        assertSuccess(response2);
        assertEquals("CANCELLED", response.getResponseBody().getInvoiceStatus());
    }
}