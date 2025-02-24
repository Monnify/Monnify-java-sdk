package com.monnify.services.limitprofile;

import com.monnify.Monnify;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.limitprofile.LimitProfileRequest;
import com.monnify.models.limitprofile.LimitProfileResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static com.monnify.MainTest.assertSuccess;
import static org.junit.jupiter.api.Assertions.*;

class LimitProfileServiceTest {
    private final LimitProfileService limitProfileService = new LimitProfileService();
    @BeforeAll
    static void setUp() {
        assertNotNull(System.getenv("apiKey"));
        assertNotNull(System.getenv("secretKey"));
        assertNotNull(System.getenv("contractCode"));
        Monnify.initialize(System.getenv("apiKey"), System.getenv("secretKey"));
    }

    private static String limitProfileCode = "";
    private static final LimitProfileRequest request = LimitProfileRequest.builder().limitProfileName("Profile " + UUID.randomUUID())
            .dailyTransactionValue(BigDecimal.valueOf(1000000))
            .dailyTransactionVolume(100)
                        .singleTransactionValue(BigDecimal.valueOf(10000))
            .build();
    @Test
    void createLimitProfile() {
        MonnifyBaseResponse<LimitProfileResponse> response = limitProfileService.createLimitProfile(request);
        assertSuccess(response);
        limitProfileCode = response.getResponseBody().getLimitProfileCode();
    }

    @Test
    void getLimitProfiles() {
        MonnifyBaseResponse<SearchResponse<LimitProfileResponse>> response = limitProfileService.getLimitProfiles();
        assertSuccess(response);
        assertFalse(response.getResponseBody().isEmpty());
    }

    @Test
    void updateLimitProfile() {
        request.setDailyTransactionVolume(32);
        MonnifyBaseResponse<LimitProfileResponse> response = limitProfileService.updateLimitProfile(limitProfileCode, request);
        assertSuccess(response);
        assertEquals(32, response.getResponseBody().getDailyTransactionVolume());
    }
}