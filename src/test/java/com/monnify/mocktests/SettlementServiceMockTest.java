package com.monnify.mocktests;

import com.monnify.BaseMonnifyMockTest;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.settlement.SettlementResponse;
import com.monnify.models.transaction.TransactionStatusResponse;
import com.monnify.services.settlement.SettlementService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Mock tests for {@link SettlementService}.
 */
public class SettlementServiceMockTest extends BaseMonnifyMockTest {

    private final SettlementService settlementService = new SettlementService();

    @Test
    void shouldGetTransactionsBySettlementReference() {

        stubFor(get(urlPathEqualTo(
                        "/api/v1/transactions/find-by-settlement-reference"
                ))
                        .withQueryParam(
                                "reference",
                                equalTo("settlement-reference-001")
                        )
                        .withQueryParam(
                                "page",
                                equalTo("0")
                        )
                        .withQueryParam(
                                "size",
                                equalTo("10")
                        )
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader(
                                                "Content-Type",
                                                "application/json"
                                        )
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\":true,"
                                                        + "\"responseMessage\":\"success\","
                                                        + "\"responseCode\":\"0\","
                                                        + "\"responseBody\":{"
                                                        + "\"content\":["
                                                        + "{"
                                                        + "\"transactionReference\":\"MNFY|123456\","
                                                        + "\"paymentReference\":\"PAY|123456\","
                                                        + "\"amountPaid\":10000.00,"
                                                        + "\"totalPayable\":10000.00,"
                                                        + "\"settlementAmount\":9900.00,"
                                                        + "\"paymentStatus\":\"PAID\","
                                                        + "\"paymentDescription\":\"Test settlement\","
                                                        + "\"currency\":\"NGN\","
                                                        + "\"paymentMethod\":\"ACCOUNT_TRANSFER\""
                                                        + "}"
                                                        + "],"
                                                        + "\"pageable\":{"
                                                        + "\"pageNumber\":0,"
                                                        + "\"pageSize\":10"
                                                        + "},"
                                                        + "\"totalElements\":1,"
                                                        + "\"totalPages\":1"
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        MonnifyBaseResponse<SearchResponse<TransactionStatusResponse>> response =
                settlementService.getTransactionsBySettlementReference(
                        "settlement-reference-001",
                        0,
                        10
                );

        assertNotNull(response);
        assertTrue(response.isRequestSuccessful());
        assertNotNull(response.getResponseBody());

        assertEquals(
                1,
                response.getResponseBody().getContent().size()
        );

        TransactionStatusResponse transaction =
                response.getResponseBody()
                        .getContent()
                        .get(0);

        assertEquals(
                "MNFY|123456",
                transaction.getTransactionReference()
        );

        assertEquals(
                "PAY|123456",
                transaction.getPaymentReference()
        );

        assertEquals(
                new BigDecimal("10000.00"),
                transaction.getAmountPaid()
        );

        assertEquals(
                new BigDecimal("9900.00"),
                transaction.getSettlementAmount()
        );

        assertEquals(
                "PAID",
                transaction.getPaymentStatus()
        );

        assertEquals(
                "NGN",
                transaction.getCurrency()
        );

        verify(
                getRequestedFor(
                        urlPathEqualTo(
                                "/api/v1/transactions/find-by-settlement-reference"
                        )
                )
                        .withQueryParam(
                                "reference",
                                equalTo("settlement-reference-001")
                        )
                        .withQueryParam(
                                "page",
                                equalTo("0")
                        )
                        .withQueryParam(
                                "size",
                                equalTo("10")
                        )
        );
    }

    @Test
    void shouldThrowExceptionWhenReferenceIsNull() {

        MonnifyValidationException exception =
                assertThrows(
                        MonnifyValidationException.class,
                        () -> settlementService
                                .getTransactionsBySettlementReference(
                                        null,
                                        0,
                                        10
                                )
                );

        assertEquals(
                "Reference is required, size must be greater than 0",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenReferenceIsEmpty() {

        MonnifyValidationException exception =
                assertThrows(
                        MonnifyValidationException.class,
                        () -> settlementService
                                .getTransactionsBySettlementReference(
                                        "",
                                        0,
                                        10
                                )
                );

        assertEquals(
                "Reference is required, size must be greater than 0",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenSizeIsZero() {

        MonnifyValidationException exception =
                assertThrows(
                        MonnifyValidationException.class,
                        () -> settlementService
                                .getTransactionsBySettlementReference(
                                        "settlement-reference-001",
                                        0,
                                        0
                                )
                );

        assertEquals(
                "Reference is required, size must be greater than 0",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenSizeIsNegative() {

        MonnifyValidationException exception =
                assertThrows(
                        MonnifyValidationException.class,
                        () -> settlementService
                                .getTransactionsBySettlementReference(
                                        "settlement-reference-001",
                                        0,
                                        -1
                                )
                );

        assertEquals(
                "Reference is required, size must be greater than 0",
                exception.getMessage()
        );
    }

    @Test
    void shouldGetSettlementInformation() {

        stubFor(get(urlPathEqualTo(
                        "/api/v1/settlement-detail"
                ))
                        .withQueryParam(
                                "transactionReference",
                                equalTo("MNFY|123456")
                        )
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader(
                                                "Content-Type",
                                                "application/json"
                                        )
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\":true,"
                                                        + "\"responseMessage\":\"success\","
                                                        + "\"responseCode\":\"0\","
                                                        + "\"responseBody\":{"
                                                        + "\"transaction\":{"
                                                        + "\"transactionReference\":\"MNFY|123456\","
                                                        + "\"paymentReference\":\"PAY|123456\","
                                                        + "\"amountPaid\":10000.00,"
                                                        + "\"totalPayable\":10000.00,"
                                                        + "\"settlementAmount\":9900.00,"
                                                        + "\"paymentStatus\":\"PAID\","
                                                        + "\"paymentDescription\":\"Test payment\","
                                                        + "\"transactionHash\":\"transaction-hash-001\","
                                                        + "\"currency\":\"NGN\","
                                                        + "\"paymentMethod\":\"ACCOUNT_TRANSFER\""
                                                        + "},"
                                                        + "\"beneficiaries\":[]"
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        MonnifyBaseResponse<SettlementResponse> response =
                settlementService.getSettlementInformation(
                        "MNFY|123456"
                );

        assertNotNull(response);
        assertTrue(response.isRequestSuccessful());
        assertNotNull(response.getResponseBody());

        SettlementResponse settlement =
                response.getResponseBody();

        assertNotNull(settlement.getTransaction());

        TransactionStatusResponse transaction =
                settlement.getTransaction();

        assertEquals(
                "MNFY|123456",
                transaction.getTransactionReference()
        );

        assertEquals(
                "PAY|123456",
                transaction.getPaymentReference()
        );

        assertEquals(
                new BigDecimal("10000.00"),
                transaction.getAmountPaid()
        );

        assertEquals(
                new BigDecimal("10000.00"),
                transaction.getTotalPayable()
        );

        assertEquals(
                new BigDecimal("9900.00"),
                transaction.getSettlementAmount()
        );

        assertEquals(
                "PAID",
                transaction.getPaymentStatus()
        );

        assertEquals(
                "NGN",
                transaction.getCurrency()
        );

        assertEquals(
                "ACCOUNT_TRANSFER",
                transaction.getPaymentMethod()
        );

        assertNotNull(
                settlement.getBeneficiaries()
        );

        assertTrue(
                settlement.getBeneficiaries().isEmpty()
        );

        verify(
                getRequestedFor(
                        urlPathEqualTo(
                                "/api/v1/settlement-detail"
                        )
                )
                        .withQueryParam(
                                "transactionReference",
                                equalTo("MNFY|123456")
                        )
        );
    }

    @Test
    void shouldGetSettlementInformationWithEncodedReference() {

        String transactionReference =
                "transaction/reference 001";

        stubFor(get(urlPathEqualTo(
                        "/api/v1/settlement-detail"
                ))
                        .withQueryParam(
                                "transactionReference",
                                equalTo(transactionReference)
                        )
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader(
                                                "Content-Type",
                                                "application/json"
                                        )
                                        .withBody(
                                                "{"
                                                        + "\"requestSuccessful\":true,"
                                                        + "\"responseMessage\":\"success\","
                                                        + "\"responseCode\":\"0\","
                                                        + "\"responseBody\":{"
                                                        + "\"transaction\":{"
                                                        + "\"transactionReference\":\"transaction/reference 001\","
                                                        + "\"paymentReference\":\"PAY-001\","
                                                        + "\"amountPaid\":5000.00,"
                                                        + "\"totalPayable\":5000.00,"
                                                        + "\"settlementAmount\":4900.00,"
                                                        + "\"paymentStatus\":\"PAID\","
                                                        + "\"currency\":\"NGN\""
                                                        + "},"
                                                        + "\"beneficiaries\":[]"
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        MonnifyBaseResponse<SettlementResponse> response =
                settlementService.getSettlementInformation(
                        transactionReference
                );

        assertNotNull(response);
        assertTrue(response.isRequestSuccessful());
        assertNotNull(response.getResponseBody());

        assertEquals(
                transactionReference,
                response.getResponseBody()
                        .getTransaction()
                        .getTransactionReference()
        );
    }
}