package com.monnify.mocktests;

import com.monnify.BaseMonnifyMockTest;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.subaccount.SubAccountRequest;
import com.monnify.models.subaccount.SubAccountResponse;
import com.monnify.models.subaccount.UpdateSubAccountRequest;
import com.monnify.services.subaccount.SubAccountService;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class SubAccountServiceTest extends BaseMonnifyMockTest {

    private final SubAccountService subAccountService = new SubAccountService();

    @Test
    void shouldCreateSubAccounts() {

        stubFor(post(urlEqualTo("/api/v1/sub-accounts"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{\n" +
                                        "  \"requestSuccessful\": true,\n" +
                                        "  \"responseMessage\": \"success\",\n" +
                                        "  \"responseCode\": \"0\",\n" +
                                        "  \"responseBody\": [\n" +
                                        "    {\n" +
                                        "      \"subAccountCode\": \"MFY_SUB_001\",\n" +
                                        "      \"accountNumber\": \"1234567890\",\n" +
                                        "      \"accountName\": \"Test Sub Account\",\n" +
                                        "      \"currencyCode\": \"NGN\",\n" +
                                        "      \"email\": \"test@example.com\",\n" +
                                        "      \"bankCode\": \"058\",\n" +
                                        "      \"bankName\": \"GTBank\",\n" +
                                        "      \"defaultSplitPercentage\": 50.0,\n" +
                                        "      \"settlementProfileCode\": \"SETTLEMENT_001\",\n" +
                                        "      \"settlementReportEmails\": [\n" +
                                        "        \"test@example.com\"\n" +
                                        "      ]\n" +
                                        "    }\n" +
                                        "  ]\n" +
                                        "}"
                        )));

        SubAccountRequest request = SubAccountRequest.builder()
                .accountNumber("1234567890")
                .currencyCode("NGN")
                .email("test@example.com")
                .bankCode("058")
                .defaultSplitPercentage(50.0)
                .build();

        List<SubAccountRequest> requests = Arrays.asList(request);

        MonnifyBaseResponse<List<SubAccountResponse>> response =
                subAccountService.createSubAccounts(requests);

        assertNotNull(response);
        assertTrue(response.isRequestSuccessful());
        assertNotNull(response.getResponseBody());
        assertEquals(1, response.getResponseBody().size());

        SubAccountResponse subAccount =
                response.getResponseBody().get(0);

        assertEquals("MFY_SUB_001", subAccount.getSubAccountCode());
        assertEquals("1234567890", subAccount.getAccountNumber());
        assertEquals("Test Sub Account", subAccount.getAccountName());
        assertEquals("NGN", subAccount.getCurrencyCode());
        assertEquals("test@example.com", subAccount.getEmail());
        assertEquals("058", subAccount.getBankCode());
        assertEquals("GTBank", subAccount.getBankName());
        assertEquals(50.0, subAccount.getDefaultSplitPercentage());

        verify(postRequestedFor(urlEqualTo("/api/v1/sub-accounts")));
    }

    @Test
    void shouldDeleteSubAccount() {

        stubFor(delete(urlEqualTo("/api/v1/sub-accounts/MFY_SUB_001"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{\n" +
                                        "  \"requestSuccessful\": true,\n" +
                                        "  \"responseMessage\": \"success\",\n" +
                                        "  \"responseCode\": \"0\",\n" +
                                        "  \"responseBody\": null\n" +
                                        "}"
                        )));

        MonnifyBaseResponse<Void> response =
                subAccountService.deleteSubAccount("MFY_SUB_001");

        assertNotNull(response);
        assertTrue(response.isRequestSuccessful());
        assertEquals("0", response.getResponseCode());

        verify(deleteRequestedFor(
                urlEqualTo("/api/v1/sub-accounts/MFY_SUB_001")
        ));
    }

    @Test
    void shouldThrowExceptionWhenDeletingSubAccountWithEmptyCode() {

        MonnifyValidationException exception =
                assertThrows(
                        MonnifyValidationException.class,
                        () -> subAccountService.deleteSubAccount("")
                );

        assertEquals("subAccountCode is empty", exception.getMessage());

        verify(0, deleteRequestedFor(anyUrl()));
    }

    @Test
    void shouldThrowExceptionWhenDeletingSubAccountWithNullCode() {

        MonnifyValidationException exception =
                assertThrows(
                        MonnifyValidationException.class,
                        () -> subAccountService.deleteSubAccount(null)
                );

        assertEquals("subAccountCode is empty", exception.getMessage());

        verify(0, deleteRequestedFor(anyUrl()));
    }

    @Test
    void shouldGetSubAccounts() {

        stubFor(get(urlEqualTo("/api/v1/sub-accounts"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{\n" +
                                        "  \"requestSuccessful\": true,\n" +
                                        "  \"responseMessage\": \"success\",\n" +
                                        "  \"responseCode\": \"0\",\n" +
                                        "  \"responseBody\": [\n" +
                                        "    {\n" +
                                        "      \"subAccountCode\": \"MFY_SUB_001\",\n" +
                                        "      \"accountNumber\": \"1234567890\",\n" +
                                        "      \"accountName\": \"Test Sub Account\",\n" +
                                        "      \"currencyCode\": \"NGN\",\n" +
                                        "      \"email\": \"test@example.com\",\n" +
                                        "      \"bankCode\": \"058\",\n" +
                                        "      \"bankName\": \"GTBank\",\n" +
                                        "      \"defaultSplitPercentage\": 50.0,\n" +
                                        "      \"settlementProfileCode\": \"SETTLEMENT_001\",\n" +
                                        "      \"settlementReportEmails\": [\n" +
                                        "        \"test@example.com\"\n" +
                                        "      ]\n" +
                                        "    },\n" +
                                        "    {\n" +
                                        "      \"subAccountCode\": \"MFY_SUB_002\",\n" +
                                        "      \"accountNumber\": \"0987654321\",\n" +
                                        "      \"accountName\": \"Second Sub Account\",\n" +
                                        "      \"currencyCode\": \"NGN\",\n" +
                                        "      \"email\": \"second@example.com\",\n" +
                                        "      \"bankCode\": \"044\",\n" +
                                        "      \"bankName\": \"Access Bank\",\n" +
                                        "      \"defaultSplitPercentage\": 30.0,\n" +
                                        "      \"settlementProfileCode\": \"SETTLEMENT_002\",\n" +
                                        "      \"settlementReportEmails\": [\n" +
                                        "        \"second@example.com\"\n" +
                                        "      ]\n" +
                                        "    }\n" +
                                        "  ]\n" +
                                        "}"
                        )));

        MonnifyBaseResponse<List<SubAccountResponse>> response =
                subAccountService.getSubAccounts();

        assertNotNull(response);
        assertTrue(response.isRequestSuccessful());
        assertNotNull(response.getResponseBody());
        assertEquals(2, response.getResponseBody().size());

        SubAccountResponse first =
                response.getResponseBody().get(0);

        assertEquals("MFY_SUB_001", first.getSubAccountCode());
        assertEquals("1234567890", first.getAccountNumber());
        assertEquals("Test Sub Account", first.getAccountName());
        assertEquals("NGN", first.getCurrencyCode());
        assertEquals("058", first.getBankCode());
        assertEquals("GTBank", first.getBankName());

        SubAccountResponse second =
                response.getResponseBody().get(1);

        assertEquals("MFY_SUB_002", second.getSubAccountCode());
        assertEquals("0987654321", second.getAccountNumber());
        assertEquals("Second Sub Account", second.getAccountName());

        verify(getRequestedFor(urlEqualTo("/api/v1/sub-accounts")));
    }

    @Test
    void shouldUpdateSubAccount() {

        stubFor(put(urlEqualTo("/api/v1/sub-accounts"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{\n" +
                                        "  \"requestSuccessful\": true,\n" +
                                        "  \"responseMessage\": \"success\",\n" +
                                        "  \"responseCode\": \"0\",\n" +
                                        "  \"responseBody\": {\n" +
                                        "    \"subAccountCode\": \"MFY_SUB_001\",\n" +
                                        "    \"accountNumber\": \"1234567890\",\n" +
                                        "    \"accountName\": \"Updated Sub Account\",\n" +
                                        "    \"currencyCode\": \"NGN\",\n" +
                                        "    \"email\": \"updated@example.com\",\n" +
                                        "    \"bankCode\": \"058\",\n" +
                                        "    \"bankName\": \"GTBank\",\n" +
                                        "    \"defaultSplitPercentage\": 70.0,\n" +
                                        "    \"settlementProfileCode\": \"SETTLEMENT_001\",\n" +
                                        "    \"settlementReportEmails\": [\n" +
                                        "      \"updated@example.com\"\n" +
                                        "    ]\n" +
                                        "  }\n" +
                                        "}"
                        )));

        UpdateSubAccountRequest request =
                UpdateSubAccountRequest.builder()
                        .subAccountCode("MFY_SUB_001")
                        .currencyCode("NGN")
                        .bankCode("058")
                        .accountNumber("1234567890")
                        .email("updated@example.com")
                        .defaultSplitPercentage(70.0)
                        .build();

        MonnifyBaseResponse<SubAccountResponse> response =
                subAccountService.updateSubAccount(request);

        assertNotNull(response);
        assertTrue(response.isRequestSuccessful());
        assertNotNull(response.getResponseBody());

        SubAccountResponse subAccount =
                response.getResponseBody();

        assertEquals("MFY_SUB_001", subAccount.getSubAccountCode());
        assertEquals("1234567890", subAccount.getAccountNumber());
        assertEquals("Updated Sub Account", subAccount.getAccountName());
        assertEquals("updated@example.com", subAccount.getEmail());
        assertEquals(70.0, subAccount.getDefaultSplitPercentage());

        verify(putRequestedFor(urlEqualTo("/api/v1/sub-accounts")));
    }
}