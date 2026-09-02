package com.monnify.mocktests;
import com.monnify.BaseMonnifyMockTest;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.account.*;
import com.monnify.models.transaction.TransactionRecord;
import com.monnify.services.reservedaccount.ReservedAccountService;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReservedAccountServiceTest extends BaseMonnifyMockTest {

    private final ReservedAccountService reservedAccountService =
            new ReservedAccountService();

    @Test
    void shouldCreateReservedAccount() {

        String response = "{"
                + "\"requestSuccessful\":true,"
                + "\"responseMessage\":\"Reserved account created successfully\","
                + "\"responseCode\":\"0\","
                + "\"responseBody\":{"
                + "\"contractCode\":\"MK_TEST\","
                + "\"accountReference\":\"reserved-account-001\","
                + "\"accountName\":\"Test Customer\","
                + "\"currencyCode\":\"NGN\","
                + "\"customerEmail\":\"test@example.com\","
                + "\"customerName\":\"Test Customer\","
                + "\"status\":\"ACTIVE\""
                + "}"
                + "}";

        stubFor(post(urlEqualTo("/api/v2/bank-transfer/reserved-accounts"))
                .willReturn(okJson(response)));

        ReservedAccountRequest request = new ReservedAccountRequest();
        request.setAccountReference("reserved-account-001");
        request.setAccountName("Test Customer");
        request.setCurrencyCode("NGN");
        request.setContractCode("MK_TEST");
        request.setCustomerEmail("test@example.com");
        request.setCustomerName("Test Customer");
        request.setGetAllAvailableBanks(true);

        MonnifyBaseResponse<ReservedAccountResponse> result =
                reservedAccountService.createReservedAccount(request);

        assertNotNull(result);
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());
        assertEquals(
                "reserved-account-001",
                result.getResponseBody().getAccountReference()
        );

        verify(postRequestedFor(
                urlEqualTo("/api/v2/bank-transfer/reserved-accounts")
        ));
    }

    @Test
    void shouldRejectReservedAccountWhenPreferredBanksAreRequired() {

        ReservedAccountRequest request = new ReservedAccountRequest();
        request.setAccountReference("reserved-account-001");
        request.setAccountName("Test Customer");
        request.setCurrencyCode("NGN");
        request.setContractCode("MK_TEST");
        request.setCustomerEmail("test@example.com");
        request.setCustomerName("Test Customer");
        request.setGetAllAvailableBanks(false);
        request.setPreferredBanks(new ArrayList<String>());

        assertThrows(
                MonnifyValidationException.class,
                () -> reservedAccountService.createReservedAccount(request)
        );
    }

    @Test
    void shouldCreateInvoiceReservedAccount() {

        String response = "{"
                + "\"requestSuccessful\":true,"
                + "\"responseMessage\":\"Reserved account created successfully\","
                + "\"responseCode\":\"0\","
                + "\"responseBody\":{"
                + "\"accountReference\":\"invoice-account-001\","
                + "\"accountName\":\"Invoice Customer\","
                + "\"currencyCode\":\"NGN\","
                + "\"status\":\"ACTIVE\""
                + "}"
                + "}";

        stubFor(post(urlEqualTo("/api/v1/bank-transfer/reserved-accounts"))
                .willReturn(okJson(response)));

        ReservedAccountRequest request = new ReservedAccountRequest();
        request.setAccountReference("invoice-account-001");
        request.setAccountName("Invoice Customer");
        request.setCurrencyCode("NGN");
        request.setContractCode("MK_TEST");
        request.setCustomerEmail("invoice@example.com");
        request.setCustomerName("Invoice Customer");
        request.setGetAllAvailableBanks(true);

        MonnifyBaseResponse<ReservedAccountResponse> result =
                reservedAccountService.createReservedAccountInvoice(request);

        assertNotNull(result);
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());
        assertEquals(
                "invoice-account-001",
                result.getResponseBody().getAccountReference()
        );

        verify(postRequestedFor(
                urlEqualTo("/api/v1/bank-transfer/reserved-accounts")
        ));
    }

    @Test
    void shouldCreateReservedAccountWithLimit() {

        String response = "{"
                + "\"requestSuccessful\":true,"
                + "\"responseMessage\":\"Reserved account created successfully\","
                + "\"responseCode\":\"0\","
                + "\"responseBody\":{"
                + "\"accountReference\":\"limit-account-001\","
                + "\"accountName\":\"Limit Customer\","
                + "\"currencyCode\":\"NGN\","
                + "\"status\":\"ACTIVE\""
                + "}"
                + "}";

        stubFor(post(urlEqualTo(
                "/api/v2/bank-transfer/reserved-accounts/limit"
        )).willReturn(okJson(response)));

        ReservedAccountRequest request = new ReservedAccountRequest();
        request.setAccountReference("limit-account-001");
        request.setAccountName("Limit Customer");
        request.setCurrencyCode("NGN");
        request.setContractCode("MK_TEST");
        request.setCustomerEmail("limit@example.com");
        request.setCustomerName("Limit Customer");
        request.setLimitProfileCode("LIMIT_PROFILE_001");
        request.setGetAllAvailableBanks(true);

        MonnifyBaseResponse<ReservedAccountResponse> result =
                reservedAccountService.createReservedAccountWithLimit(request);

        assertNotNull(result);
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());
        assertEquals(
                "limit-account-001",
                result.getResponseBody().getAccountReference()
        );

        verify(postRequestedFor(
                urlEqualTo("/api/v2/bank-transfer/reserved-accounts/limit")
        ));
    }

    @Test
    void shouldUpdateReservedAccountLimit() {

        String response = "{"
                + "\"requestSuccessful\":true,"
                + "\"responseMessage\":\"Limit profile updated successfully\","
                + "\"responseCode\":\"0\","
                + "\"responseBody\":{"
                + "\"accountReference\":\"reserved-account-001\","
                + "\"status\":\"ACTIVE\""
                + "}"
                + "}";

        stubFor(put(urlEqualTo(
                "/api/v2/bank-transfer/reserved-accounts/limit"
        )).willReturn(okJson(response)));

        UpdateReservedAccountLimitRequest request =
                new UpdateReservedAccountLimitRequest();

        request.setAccountReference("reserved-account-001");
        request.setLimitProfileCode("LIMIT_PROFILE_001");

        MonnifyBaseResponse<ReservedAccountResponse> result =
                reservedAccountService.updateReservedAccountLimit(request);

        assertNotNull(result);
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());
        assertEquals(
                "reserved-account-001",
                result.getResponseBody().getAccountReference()
        );

        verify(putRequestedFor(
                urlEqualTo("/api/v2/bank-transfer/reserved-accounts/limit")
        ));
    }

    @Test
    void shouldRejectUpdateReservedAccountLimitWithMissingAccountReference() {

        UpdateReservedAccountLimitRequest request =
                new UpdateReservedAccountLimitRequest();

        request.setLimitProfileCode("LIMIT_PROFILE_001");

        assertThrows(
                MonnifyValidationException.class,
                () -> reservedAccountService.updateReservedAccountLimit(request)
        );
    }

    @Test
    void shouldGetReservedAccountDetails() {

        String response = "{"
                + "\"requestSuccessful\":true,"
                + "\"responseMessage\":\"Reserved account retrieved successfully\","
                + "\"responseCode\":\"0\","
                + "\"responseBody\":{"
                + "\"accountReference\":\"reserved-account-001\","
                + "\"accountName\":\"Test Customer\","
                + "\"currencyCode\":\"NGN\","
                + "\"customerEmail\":\"test@example.com\","
                + "\"status\":\"ACTIVE\""
                + "}"
                + "}";

        stubFor(get(urlEqualTo(
                "/api/v2/bank-transfer/reserved-accounts/reserved-account-001"
        )).willReturn(okJson(response)));

        MonnifyBaseResponse<ReservedAccountResponse> result =
                reservedAccountService.getReservedAccountDetails(
                        "reserved-account-001"
                );

        assertNotNull(result);
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());
        assertEquals(
                "reserved-account-001",
                result.getResponseBody().getAccountReference()
        );

        verify(getRequestedFor(
                urlEqualTo(
                        "/api/v2/bank-transfer/reserved-accounts/reserved-account-001"
                )
        ));
    }

    @Test
    void shouldRejectGetReservedAccountDetailsWithoutReference() {

        assertThrows(
                MonnifyValidationException.class,
                () -> reservedAccountService.getReservedAccountDetails("")
        );
    }

    @Test
    void shouldDeallocateReservedAccount() {

        String response = "{"
                + "\"requestSuccessful\":true,"
                + "\"responseMessage\":\"Reserved account deallocated successfully\","
                + "\"responseCode\":\"0\","
                + "\"responseBody\":{"
                + "\"accountReference\":\"reserved-account-001\","
                + "\"status\":\"DEALLOCATED\""
                + "}"
                + "}";

        stubFor(delete(urlEqualTo(
                "/api/v1/bank-transfer/reserved-accounts/reference/reserved-account-001"
        )).willReturn(okJson(response)));

        MonnifyBaseResponse<ReservedAccountResponse> result =
                reservedAccountService.deallocateReservedAccount(
                        "reserved-account-001"
                );

        assertNotNull(result);
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());
        assertEquals(
                "reserved-account-001",
                result.getResponseBody().getAccountReference()
        );

        verify(deleteRequestedFor(
                urlEqualTo(
                        "/api/v1/bank-transfer/reserved-accounts/reference/reserved-account-001"
                )
        ));
    }

    @Test
    void shouldRejectDeallocateReservedAccountWithoutReference() {

        assertThrows(
                MonnifyValidationException.class,
                () -> reservedAccountService.deallocateReservedAccount(null)
        );
    }

    @Test
    void shouldUpdateKycInfoWithBvn() {

        String response = "{"
                + "\"requestSuccessful\":true,"
                + "\"responseMessage\":\"KYC information updated successfully\","
                + "\"responseCode\":\"0\","
                + "\"responseBody\":{"
                + "\"accountReference\":\"reserved-account-001\","
                + "\"accountName\":\"Test Customer\","
                + "\"customerEmail\":\"test@example.com\","
                + "\"customerName\":\"Test Customer\","
                + "\"bvn\":\"12345678901\""
                + "}"
                + "}";

        stubFor(put(urlEqualTo(
                "/api/v1/bank-transfer/reserved-accounts/"
                        + "reserved-account-001/kyc-info"
        )).willReturn(okJson(response)));

        KycInfoRequest request = new KycInfoRequest();
        request.setBvn("12345678901");

        MonnifyBaseResponse<KycInfoResponse> result =
                reservedAccountService.updateKycInfo(
                        request,
                        "reserved-account-001"
                );

        assertNotNull(result);
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());
        assertEquals(
                "reserved-account-001",
                result.getResponseBody().getAccountReference()
        );
        assertEquals(
                "12345678901",
                result.getResponseBody().getBvn()
        );

        verify(putRequestedFor(
                urlEqualTo(
                        "/api/v1/bank-transfer/reserved-accounts/"
                                + "reserved-account-001/kyc-info"
                )
        ));
    }

    @Test
    void shouldRejectKycUpdateWithoutBvnOrNin() {

        KycInfoRequest request = new KycInfoRequest();

        assertThrows(
                MonnifyValidationException.class,
                () -> reservedAccountService.updateKycInfo(
                        request,
                        "reserved-account-001"
                )
        );
    }

    @Test
    void shouldRejectKycUpdateWithoutAccountReference() {

        KycInfoRequest request = new KycInfoRequest();
        request.setBvn("12345678901");

        assertThrows(
                MonnifyValidationException.class,
                () -> reservedAccountService.updateKycInfo(
                        request,
                        ""
                )
        );
    }

    @Test
    void shouldGetReservedAccountTransactions() {

        stubFor(get(urlPathEqualTo(
                "/api/v1/bank-transfer/reserved-accounts/transactions"))
                .withQueryParam("accountReference",
                        equalTo("reserved-account-001"))
                .withQueryParam("page",
                        equalTo("0"))
                .withQueryParam("size",
                        equalTo("10"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{\n" +
                                        "  \"requestSuccessful\": true,\n" +
                                        "  \"responseMessage\": \"success\",\n" +
                                        "  \"responseCode\": \"0\",\n" +
                                        "  \"responseBody\": {\n" +
                                        "    \"content\": [],\n" +
                                        "    \"pageable\": {\n" +
                                        "      \"pageNumber\": 0,\n" +
                                        "      \"pageSize\": 10\n" +
                                        "    },\n" +
                                        "    \"totalElements\": 0,\n" +
                                        "    \"totalPages\": 0,\n" +
                                        "    \"last\": true,\n" +
                                        "    \"first\": true,\n" +
                                        "    \"numberOfElements\": 0,\n" +
                                        "    \"size\": 10,\n" +
                                        "    \"number\": 0\n" +
                                        "  }\n" +
                                        "}")));

        MonnifyBaseResponse<SearchResponse<TransactionRecord>> response =
                reservedAccountService.getReservedAccountTransactions(
                        "reserved-account-001",
                        0,
                        10);

        assertNotNull(response);
        assertTrue(response.isRequestSuccessful());
        assertEquals("success", response.getResponseMessage());
        assertEquals("0", response.getResponseCode());
        assertNotNull(response.getResponseBody());
        assertNotNull(response.getResponseBody().getContent());
        assertTrue(response.getResponseBody().getContent().isEmpty());
    }

    @Test
    void shouldRejectReservedAccountTransactionsWithoutReference() {

        assertThrows(
                MonnifyValidationException.class,
                () -> reservedAccountService.getReservedAccountTransactions(
                        "",
                        0,
                        10
                )
        );
    }
}
