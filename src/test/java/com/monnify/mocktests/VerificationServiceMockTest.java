package com.monnify.mocktests;
import com.monnify.BaseMonnifyMockTest;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.verification.*;
import com.monnify.services.verification.VerificationService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;


public class VerificationServiceMockTest extends BaseMonnifyMockTest {

    private final VerificationService verificationService =
            new VerificationService();

    @Test
    void shouldValidateBankAccount() {

        String response = "{"
                + "\"requestSuccessful\":true,"
                + "\"responseMessage\":\"success\","
                + "\"responseCode\":\"0\","
                + "\"responseBody\":{"
                + "\"accountNumber\":\"0123456789\","
                + "\"accountName\":\"JOHN DOE\","
                + "\"bankCode\":\"058\""
                + "}"
                + "}";

        stubFor(get(urlPathEqualTo(
                "/api/v2/disbursements/account/validate"))
                .withQueryParam(
                        "accountNumber",
                        equalTo("0123456789"))
                .withQueryParam(
                        "bankCode",
                        equalTo("058"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(
                                "Content-Type",
                                "application/json")
                        .withBody(response)));

        MonnifyBaseResponse<BankValidationResponse> result =
                verificationService.validateBankAccount(
                        "0123456789",
                        "058"
                );

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());

        assertEquals(
                "0123456789",
                result.getResponseBody().getAccountNumber()
        );

        assertEquals(
                "JOHN DOE",
                result.getResponseBody().getAccountName()
        );

        assertEquals(
                "058",
                result.getResponseBody().getBankCode()
        );

        verify(getRequestedFor(
                urlPathEqualTo(
                        "/api/v2/disbursements/account/validate"))
                .withQueryParam(
                        "accountNumber",
                        equalTo("0123456789"))
                .withQueryParam(
                        "bankCode",
                        equalTo("058"))
        );
    }

    @Test
    void shouldThrowExceptionWhenAccountNumberIsEmpty() {

        assertThrows(
                MonnifyValidationException.class,
                () -> verificationService.validateBankAccount(
                        "",
                        "058"
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenBankCodeIsEmpty() {

        assertThrows(
                MonnifyValidationException.class,
                () -> verificationService.validateBankAccount(
                        "0123456789",
                        ""
                )
        );
    }

    @Test
    void shouldVerifyBVNInformation() {

        String response = "{"
                + "\"requestSuccessful\": true,"
                + "\"responseMessage\": \"success\","
                + "\"responseCode\": \"0\","
                + "\"responseBody\": {"
                + "\"bvn\": \"22222222222\","
                + "\"name\": {"
                + "\"matchStatus\": \"FULL_MATCH\","
                + "\"matchPercentage\": 100.0"
                + "},"
                + "\"dateOfBirth\": \"1990-01-01\","
                + "\"mobileNo\": \"08012345678\""
                + "}"
                + "}";

        stubFor(post(urlEqualTo("/api/v1/vas/bvn-details-match"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        BVNVerificationRequest request = BVNVerificationRequest.builder()
                .bvn("22222222222")
                .name("John Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .mobileNo("08012345678")
                .build();

        MonnifyBaseResponse<BVNVerificationResponse> result =
                verificationService.verifyBVNInformation(request);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertEquals("success", result.getResponseMessage());

        assertNotNull(result.getResponseBody());

        BVNVerificationResponse responseBody = result.getResponseBody();

        assertEquals("22222222222", responseBody.getBvn());
        assertEquals("1990-01-01", responseBody.getDateOfBirth());
        assertEquals("08012345678", responseBody.getMobileNo());

        assertNotNull(responseBody.getName());
        assertEquals("FULL_MATCH", responseBody.getName().getMatchStatus());
        assertEquals(
                100.0,
                responseBody.getName().getMatchPercentage(),
                0.001
        );

        verify(postRequestedFor(
                urlEqualTo("/api/v1/vas/bvn-details-match")
        ));
    }

    @Test
    void shouldBVNAccountNameMatch() {

        BVNAccountMatchRequest request =
                BVNAccountMatchRequest.builder()
                        .bankCode("058")
                        .accountNumber("0123456789")
                        .bvn("22222222222")
                        .build();

        String response = "{"
                + "\"requestSuccessful\":true,"
                + "\"responseMessage\":\"success\","
                + "\"responseCode\":\"0\","
                + "\"responseBody\":{"
                + "\"accountNumber\":\"0123456789\","
                + "\"accountName\":\"JOHN DOE\","
                + "\"bvn\":\"22222222222\","
                + "\"matchStatus\":\"FULL_MATCH\","
                + "\"matchPercentage\":100.0"
                + "}"
                + "}";

        stubFor(post(urlEqualTo(
                "/api/v1/vas/bvn-account-match"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(
                                "Content-Type",
                                "application/json")
                        .withBody(response)));

        MonnifyBaseResponse<BVNAccountMatchResponse> result =
                verificationService.bvnAccountNameMatch(request);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());

        verify(postRequestedFor(
                urlEqualTo(
                        "/api/v1/vas/bvn-account-match")));
    }

    @Test
    void shouldVerifyNIN() {

        NINVerificationRequest request =
                NINVerificationRequest.builder()
                        .nin("12345678901")
                        .build();

        String response = "{"
                + "\"requestSuccessful\":true,"
                + "\"responseMessage\":\"success\","
                + "\"responseCode\":\"0\","
                + "\"responseBody\":{"
                + "\"nin\":\"12345678901\","
                + "\"lastName\":\"DOE\","
                + "\"firstName\":\"JOHN\","
                + "\"middleName\":\"MICHAEL\","
                + "\"dateOfBirth\":\"1995-01-01\","
                + "\"gender\":\"MALE\","
                + "\"mobileNumber\":\"08012345678\""
                + "}"
                + "}";

        stubFor(post(urlEqualTo(
                "/api/v1/vas/nin-details"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(
                                "Content-Type",
                                "application/json")
                        .withBody(response)));

        MonnifyBaseResponse<NINVerificationResponse> result =
                verificationService.verifyNIN(request);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("0", result.getResponseCode());
        assertNotNull(result.getResponseBody());

        assertEquals(
                "12345678901",
                result.getResponseBody().getNin()
        );

        assertEquals(
                "DOE",
                result.getResponseBody().getLastName()
        );

        assertEquals(
                "JOHN",
                result.getResponseBody().getFirstName()
        );

        assertEquals(
                "MICHAEL",
                result.getResponseBody().getMiddleName()
        );

        assertEquals(
                "1995-01-01",
                result.getResponseBody().getDateOfBirth()
        );

        assertEquals(
                "MALE",
                result.getResponseBody().getGender()
        );

        assertEquals(
                "08012345678",
                result.getResponseBody().getMobileNumber()
        );

        verify(postRequestedFor(
                urlEqualTo(
                        "/api/v1/vas/nin-details")));
    }

    @Test
    void shouldThrowExceptionWhenBVNIsInvalid() {

        BVNAccountMatchRequest request =
                BVNAccountMatchRequest.builder()
                        .bankCode("058")
                        .accountNumber("0123456789")
                        .bvn("12345")
                        .build();

        assertThrows(
                MonnifyValidationException.class,
                () -> verificationService.bvnAccountNameMatch(request)
        );
    }

    @Test
    void shouldThrowExceptionWhenBVNAccountNumberIsEmpty() {

        BVNAccountMatchRequest request =
                BVNAccountMatchRequest.builder()
                        .bankCode("058")
                        .accountNumber("")
                        .bvn("22222222222")
                        .build();

        assertThrows(
                MonnifyValidationException.class,
                () -> verificationService.bvnAccountNameMatch(request)
        );
    }

    @Test
    void shouldThrowExceptionWhenBVNBankCodeIsEmpty() {

        BVNAccountMatchRequest request =
                BVNAccountMatchRequest.builder()
                        .bankCode("")
                        .accountNumber("0123456789")
                        .bvn("22222222222")
                        .build();

        assertThrows(
                MonnifyValidationException.class,
                () -> verificationService.bvnAccountNameMatch(request)
        );
    }

    @Test
    void shouldThrowExceptionWhenNINIsInvalid() {

        NINVerificationRequest request =
                NINVerificationRequest.builder()
                        .nin("12345")
                        .build();

        assertThrows(
                MonnifyValidationException.class,
                () -> verificationService.verifyNIN(request)
        );
    }

    @Test
    void shouldThrowExceptionWhenNINIsEmpty() {

        NINVerificationRequest request =
                NINVerificationRequest.builder()
                        .nin("")
                        .build();

        assertThrows(
                MonnifyValidationException.class,
                () -> verificationService.verifyNIN(request)
        );
    }
}
