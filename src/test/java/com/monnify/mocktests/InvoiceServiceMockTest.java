package com.monnify.mocktests;

import com.monnify.BaseMonnifyMockTest;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.invoice.InvoiceRequest;
import com.monnify.models.invoice.InvoiceResponse;
import com.monnify.services.invoice.InvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.deleteRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvoiceServiceMockTest extends BaseMonnifyMockTest {

    private InvoiceService invoiceService;

    @BeforeEach
    void setUp() {
        invoiceService = new InvoiceService();
    }

    @Test
    void shouldCreateInvoiceSuccessfully() {

        stubFor(
                post(urlPathEqualTo("/api/v1/invoice/create"))
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
                                                        + "\"amount\":5000,"
                                                        + "\"invoiceReference\":\"INV123\","
                                                        + "\"invoiceStatus\":\"PENDING\","
                                                        + "\"description\":\"Test invoice\","
                                                        + "\"contractCode\":\"CONTRACT123\","
                                                        + "\"customerEmail\":\"customer@example.com\","
                                                        + "\"customerName\":\"Test Customer\","
                                                        + "\"expiryDate\":\"2026-08-20T23:59:59\","
                                                        + "\"createdBy\":\"test-user\","
                                                        + "\"createdOn\":\"2026-08-13T10:00:00\","
                                                        + "\"checkoutUrl\":\"https://checkout.example.com/INV123\","
                                                        + "\"accountNumber\":\"1234567890\","
                                                        + "\"accountName\":\"Test Customer\","
                                                        + "\"bankName\":\"Test Bank\","
                                                        + "\"bankCode\":\"058\","
                                                        + "\"redirectUrl\":\"https://example.com/callback\","
                                                        + "\"transactionReference\":\"TXN123\""
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        InvoiceRequest request = createValidInvoiceRequest();

        MonnifyBaseResponse<InvoiceResponse> result =
                invoiceService.createInvoice(request);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertEquals("success", result.getResponseMessage());

        assertNotNull(result.getResponseBody());

        assertEquals(
                new BigDecimal("5000"),
                result.getResponseBody().getAmount()
        );

        assertEquals(
                "INV123",
                result.getResponseBody().getInvoiceReference()
        );

        assertEquals(
                "PENDING",
                result.getResponseBody().getInvoiceStatus()
        );

        assertEquals(
                "customer@example.com",
                result.getResponseBody().getCustomerEmail()
        );

        assertEquals(
                "Test Customer",
                result.getResponseBody().getCustomerName()
        );

        assertEquals(
                "1234567890",
                result.getResponseBody().getAccountNumber()
        );

        assertEquals(
                "058",
                result.getResponseBody().getBankCode()
        );

        assertEquals(
                "TXN123",
                result.getResponseBody().getTransactionReference()
        );

        verify(
                1,
                postRequestedFor(
                        urlPathEqualTo("/api/v1/invoice/create")
                )
        );
    }

    @Test
    void shouldRejectInvalidCreateInvoiceRequest() {

        InvoiceRequest request = new InvoiceRequest();

        assertThrows(
                Exception.class,
                () -> invoiceService.createInvoice(request)
        );

        verify(
                0,
                postRequestedFor(
                        urlPathEqualTo("/api/v1/invoice/create")
                )
        );
    }

    @Test
    void shouldAttachInvoiceToReservedAccountSuccessfully() {

        stubFor(
                post(urlPathEqualTo("/api/v1/invoice/create"))
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
                                                        + "\"amount\":5000,"
                                                        + "\"invoiceReference\":\"INV123\","
                                                        + "\"invoiceStatus\":\"PENDING\","
                                                        + "\"accountReference\":\"ACC123\","
                                                        + "\"accountNumber\":\"1234567890\","
                                                        + "\"accountName\":\"Test Customer\","
                                                        + "\"bankName\":\"Test Bank\","
                                                        + "\"bankCode\":\"058\""
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        InvoiceRequest request = createValidInvoiceRequest();
        request.setAccountReference("ACC123");

        MonnifyBaseResponse<InvoiceResponse> result =
                invoiceService.attachInvoiceToReservedAccount(request);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());

        assertNotNull(result.getResponseBody());

        assertEquals(
                "INV123",
                result.getResponseBody().getInvoiceReference()
        );

        assertEquals(
                "1234567890",
                result.getResponseBody().getAccountNumber()
        );

        verify(
                1,
                postRequestedFor(
                        urlPathEqualTo("/api/v1/invoice/create")
                )
        );
    }

    @Test
    void shouldRejectInvoiceWithoutAccountReference() {

        InvoiceRequest request = createValidInvoiceRequest();
        request.setAccountReference(null);

        assertThrows(
                MonnifyValidationException.class,
                () -> invoiceService.attachInvoiceToReservedAccount(request)
        );

        verify(
                0,
                postRequestedFor(
                        urlPathEqualTo("/api/v1/invoice/create")
                )
        );
    }

    @Test
    void shouldRejectEmptyAccountReference() {

        InvoiceRequest request = createValidInvoiceRequest();
        request.setAccountReference("");

        assertThrows(
                MonnifyValidationException.class,
                () -> invoiceService.attachInvoiceToReservedAccount(request)
        );

        verify(
                0,
                postRequestedFor(
                        urlPathEqualTo("/api/v1/invoice/create")
                )
        );
    }

    @Test
    void shouldViewInvoiceDetailsSuccessfully() {

        stubFor(
                get(urlPathEqualTo("/api/v1/invoice/INV123/details"))
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
                                                        + "\"amount\":5000,"
                                                        + "\"invoiceReference\":\"INV123\","
                                                        + "\"invoiceStatus\":\"PENDING\","
                                                        + "\"description\":\"Test invoice\","
                                                        + "\"contractCode\":\"CONTRACT123\","
                                                        + "\"customerEmail\":\"customer@example.com\","
                                                        + "\"customerName\":\"Test Customer\","
                                                        + "\"transactionReference\":\"TXN123\""
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        MonnifyBaseResponse<InvoiceResponse> result =
                invoiceService.viewInvoiceDetails("INV123");

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());

        assertNotNull(result.getResponseBody());

        assertEquals(
                "INV123",
                result.getResponseBody().getInvoiceReference()
        );

        assertEquals(
                "PENDING",
                result.getResponseBody().getInvoiceStatus()
        );

        assertEquals(
                "TXN123",
                result.getResponseBody().getTransactionReference()
        );

        verify(
                1,
                getRequestedFor(
                        urlPathEqualTo("/api/v1/invoice/INV123/details")
                )
        );
    }


    @Test
    void shouldRejectEmptyInvoiceReferenceForViewDetails() {

        assertThrows(
                MonnifyValidationException.class,
                () -> invoiceService.viewInvoiceDetails("")
        );

        verify(
                0,
                getRequestedFor(
                        urlPathMatching("/api/v1/invoice/.*/details")
                )
        );
    }

    @Test
    void shouldRejectNullInvoiceReferenceForViewDetails() {

        assertThrows(
                MonnifyValidationException.class,
                () -> invoiceService.viewInvoiceDetails(null)
        );

        verify(
                0,
                getRequestedFor(
                        urlPathMatching("/api/v1/invoice/.*/details")
                )
        );
    }

    @Test
    void shouldGetAllInvoicesSuccessfully() {

        stubFor(
                get(urlPathEqualTo("/api/v1/invoice/all"))
                        .withQueryParam("page", equalTo("0"))
                        .withQueryParam("size", equalTo("10"))
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
                                                        + "\"content\":[],"
                                                        + "\"pageNumber\":0,"
                                                        + "\"pageSize\":10,"
                                                        + "\"totalElements\":0,"
                                                        + "\"totalPages\":0"
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        MonnifyBaseResponse<SearchResponse<InvoiceResponse>> result =
                invoiceService.getAllInvoices(0, 10);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());

        verify(
                1,
                getRequestedFor(
                        urlPathEqualTo("/api/v1/invoice/all")
                )
                        .withQueryParam("page", equalTo("0"))
                        .withQueryParam("size", equalTo("10"))
        );
    }

    @Test
    void shouldCancelInvoiceSuccessfully() {

        stubFor(
                delete(urlPathEqualTo("/api/v1/invoice/INV123/cancel"))
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
                                                        + "\"amount\":5000,"
                                                        + "\"invoiceReference\":\"INV123\","
                                                        + "\"invoiceStatus\":\"CANCELLED\","
                                                        + "\"customerName\":\"Test Customer\""
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        MonnifyBaseResponse<InvoiceResponse> result =
                invoiceService.cancelInvoice("INV123");

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());

        assertNotNull(result.getResponseBody());

        assertEquals(
                "INV123",
                result.getResponseBody().getInvoiceReference()
        );

        assertEquals(
                "CANCELLED",
                result.getResponseBody().getInvoiceStatus()
        );

        verify(
                1,
                deleteRequestedFor(
                        urlPathEqualTo("/api/v1/invoice/INV123/cancel")
                )
        );
    }

    @Test
    void shouldRejectEmptyInvoiceReferenceWhenCancelling() {

        assertThrows(
                MonnifyValidationException.class,
                () -> invoiceService.cancelInvoice("")
        );

        verify(
                0,
                deleteRequestedFor(
                        urlPathMatching("/api/v1/invoice/.*/cancel")
                )
        );
    }

    @Test
    void shouldRejectNullInvoiceReferenceWhenCancelling() {

        assertThrows(
                MonnifyValidationException.class,
                () -> invoiceService.cancelInvoice(null)
        );

        verify(
                0,
                deleteRequestedFor(
                        urlPathMatching("/api/v1/invoice/.*/cancel")
                )
        );
    }

    @Test
    void shouldReturnErrorResponseWhenCreateInvoiceFails() {

        stubFor(
                post(urlPathEqualTo("/api/v1/invoice/create"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\":false,"
                                                        + "\"responseMessage\":\"Invalid invoice\","
                                                        + "\"responseCode\":\"99\","
                                                        + "\"responseBody\":null"
                                                        + "}"
                                        )
                        )
        );

        InvoiceRequest request = createValidInvoiceRequest();

        MonnifyBaseResponse<InvoiceResponse> result =
                invoiceService.createInvoice(request);

        assertNotNull(result);
        assertFalse(result.isRequestSuccessful());
        assertEquals("99", result.getResponseCode());
        assertEquals(
                "Invalid invoice",
                result.getResponseMessage()
        );

        verify(
                1,
                postRequestedFor(
                        urlPathEqualTo("/api/v1/invoice/create")
                )
        );
    }

    private InvoiceRequest createValidInvoiceRequest() {

        InvoiceRequest request = new InvoiceRequest();

        request.setAmount(new BigDecimal("5000.00"));
        request.setInvoiceReference("INV123");
        request.setDescription("Test invoice");
        request.setCurrencyCode("NGN");
        request.setContractCode("CONTRACT123");
        request.setCustomerEmail("customer@example.com");
        request.setCustomerName("Test Customer");
        request.setExpiryDate("2026-08-20T23:59:59");
        request.setRedirectUrl("https://example.com/callback");

        return request;
    }
}