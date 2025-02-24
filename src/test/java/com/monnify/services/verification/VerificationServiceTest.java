package com.monnify.services.verification;

import com.monnify.Monnify;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.verification.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.monnify.MainTest.assertSuccess;
import static org.junit.jupiter.api.Assertions.*;

class VerificationServiceTest {
    private final VerificationService verificationService = new VerificationService();
    @BeforeAll
    static void setUp() {
        assertNotNull(System.getenv("apiKey"));
        assertNotNull(System.getenv("secretKey"));
        assertNotNull(System.getenv("contractCode"));
        Monnify.initialize(System.getenv("apiKey"), System.getenv("secretKey"));
    }

    @Test
    void validateBankAccount() {
        MonnifyBaseResponse<BankValidationResponse> response = verificationService.validateBankAccount("", "");
        assertSuccess(response);
    }

    @Test
    void verifyBVNInformation() {
        MonnifyBaseResponse<BVNVerificationResponse> response = verificationService.verifyBVNInformation(BVNVerificationRequest.builder().build());
        assertSuccess(response);
    }

    @Test
    void bvnAccountNameMatch() {
        MonnifyBaseResponse<BVNAccountMatchResponse> response = verificationService.bvnAccountNameMatch(BVNAccountMatchRequest.builder().build());
        assertSuccess(response);
    }

    @Test
    void verifyNIN() {
        MonnifyBaseResponse<NINVerificationResponse> response = verificationService.verifyNIN(new NINVerificationRequest(""));
        assertSuccess(response);
    }
}