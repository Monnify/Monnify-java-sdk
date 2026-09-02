package com.monnify.mocktests;

import com.monnify.BaseMonnifyMockTest;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.limitprofile.LimitProfileRequest;
import com.monnify.models.limitprofile.LimitProfileResponse;
import com.monnify.services.limitprofile.LimitProfileService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

class LimitProfileServiceMockTest extends BaseMonnifyMockTest {

    private final LimitProfileService limitProfileService =
            new LimitProfileService();

    @Test
    void shouldCreateLimitProfile() {

        String requestBody =
                "{"
                        + "\"limitProfileName\":\"Test Profile\","
                        + "\"singleTransactionValue\":100000.00,"
                        + "\"dailyTransactionVolume\":10,"
                        + "\"dailyTransactionValue\":500000.00"
                        + "}";

        stubFor(
                post(urlPathEqualTo("/api/v1/limit-profile"))
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
                                                        + "\"limitProfileCode\":\"LP001\","
                                                        + "\"limitProfileName\":\"Test Profile\","
                                                        + "\"singleTransactionValue\":100000.00,"
                                                        + "\"dailyTransactionVolume\":10,"
                                                        + "\"dailyTransactionValue\":500000.00,"
                                                        + "\"dateCreated\":\"2026-08-14T10:00:00\","
                                                        + "\"lastModified\":\"2026-08-14T10:00:00\""
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        LimitProfileRequest request =
                LimitProfileRequest.builder()
                        .limitProfileName("Test Profile")
                        .singleTransactionValue(new BigDecimal("100000.00"))
                        .dailyTransactionVolume(10)
                        .dailyTransactionValue(new BigDecimal("500000.00"))
                        .build();

        MonnifyBaseResponse<LimitProfileResponse> result =
                limitProfileService.createLimitProfile(request);

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("success", result.getResponseMessage());
        assertEquals("0", result.getResponseCode());

        assertNotNull(result.getResponseBody());

        assertEquals(
                "LP001",
                result.getResponseBody().getLimitProfileCode()
        );

        assertEquals(
                "Test Profile",
                result.getResponseBody().getLimitProfileName()
        );

        assertEquals(
                new BigDecimal("100000.00"),
                result.getResponseBody().getSingleTransactionValue()
        );

        assertEquals(
                10,
                result.getResponseBody().getDailyTransactionVolume()
        );

        assertEquals(
                new BigDecimal("500000.00"),
                result.getResponseBody().getDailyTransactionValue()
        );

        verify(
                1,
                postRequestedFor(
                        urlPathEqualTo("/api/v1/limit-profile")
                ).withRequestBody(
                        equalToJson(requestBody)
                )
        );
    }

    @Test
    void shouldGetLimitProfiles() {

        stubFor(
                get(urlPathEqualTo("/api/v1/limit-profile"))
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
                                                        + "\"content\":["
                                                        + "{"
                                                        + "\"limitProfileCode\":\"LP001\","
                                                        + "\"limitProfileName\":\"Test Profile\","
                                                        + "\"singleTransactionValue\":100000.00,"
                                                        + "\"dailyTransactionVolume\":10,"
                                                        + "\"dailyTransactionValue\":500000.00,"
                                                        + "\"dateCreated\":\"2026-08-14T10:00:00\","
                                                        + "\"lastModified\":\"2026-08-14T10:00:00\""
                                                        + "}"
                                                        + "],"
                                                        + "\"pageNumber\":0,"
                                                        + "\"pageSize\":10,"
                                                        + "\"totalPages\":1,"
                                                        + "\"totalElements\":1"
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        MonnifyBaseResponse<SearchResponse<LimitProfileResponse>> result =
                limitProfileService.getLimitProfiles();

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("success", result.getResponseMessage());
        assertEquals("0", result.getResponseCode());

        assertNotNull(result.getResponseBody());

        assertNotNull(result.getResponseBody().getContent());
        assertEquals(1, result.getResponseBody().getContent().size());

        LimitProfileResponse profile =
                result.getResponseBody().getContent().get(0);

        assertEquals(
                "LP001",
                profile.getLimitProfileCode()
        );

        assertEquals(
                "Test Profile",
                profile.getLimitProfileName()
        );

        assertEquals(
                new BigDecimal("100000.00"),
                profile.getSingleTransactionValue()
        );

        assertEquals(
                10,
                profile.getDailyTransactionVolume()
        );

        assertEquals(
                new BigDecimal("500000.00"),
                profile.getDailyTransactionValue()
        );

        verify(
                1,
                getRequestedFor(
                        urlPathEqualTo("/api/v1/limit-profile")
                )
        );
    }

    @Test
    void shouldUpdateLimitProfile() {

        String limitProfileCode = "LP001";

        String requestBody =
                "{"
                        + "\"limitProfileName\":\"Updated Profile\","
                        + "\"singleTransactionValue\":200000.00,"
                        + "\"dailyTransactionVolume\":20,"
                        + "\"dailyTransactionValue\":1000000.00"
                        + "}";

        stubFor(
                put(urlPathEqualTo("/api/v1/limit-profile/LP001"))
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
                                                        + "\"limitProfileCode\":\"LP001\","
                                                        + "\"limitProfileName\":\"Updated Profile\","
                                                        + "\"singleTransactionValue\":200000.00,"
                                                        + "\"dailyTransactionVolume\":20,"
                                                        + "\"dailyTransactionValue\":1000000.00,"
                                                        + "\"dateCreated\":\"2026-08-14T10:00:00\","
                                                        + "\"lastModified\":\"2026-08-14T11:00:00\""
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        LimitProfileRequest request =
                LimitProfileRequest.builder()
                        .limitProfileName("Updated Profile")
                        .singleTransactionValue(new BigDecimal("200000.00"))
                        .dailyTransactionVolume(20)
                        .dailyTransactionValue(new BigDecimal("1000000.00"))
                        .build();

        MonnifyBaseResponse<LimitProfileResponse> result =
                limitProfileService.updateLimitProfile(
                        limitProfileCode,
                        request
                );

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
        assertEquals("success", result.getResponseMessage());
        assertEquals("0", result.getResponseCode());

        assertNotNull(result.getResponseBody());

        assertEquals(
                "LP001",
                result.getResponseBody().getLimitProfileCode()
        );

        assertEquals(
                "Updated Profile",
                result.getResponseBody().getLimitProfileName()
        );

        assertEquals(
                new BigDecimal("200000.00"),
                result.getResponseBody().getSingleTransactionValue()
        );

        assertEquals(
                20,
                result.getResponseBody().getDailyTransactionVolume()
        );

        assertEquals(
                new BigDecimal("1000000.00"),
                result.getResponseBody().getDailyTransactionValue()
        );

        verify(
                1,
                putRequestedFor(
                        urlPathEqualTo("/api/v1/limit-profile/LP001")
                ).withRequestBody(
                        equalToJson(requestBody)
                )
        );
    }

    @Test
    void shouldEncodeLimitProfileCodeInUrlPath() {

        String limitProfileCode = "LP 001";

        stubFor(
                put(urlPathEqualTo("/api/v1/limit-profile/LP+001"))
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
                                                        + "\"limitProfileCode\":\"LP 001\","
                                                        + "\"limitProfileName\":\"Test Profile\","
                                                        + "\"singleTransactionValue\":100000.00,"
                                                        + "\"dailyTransactionVolume\":10,"
                                                        + "\"dailyTransactionValue\":500000.00,"
                                                        + "\"dateCreated\":\"2026-08-14T10:00:00\","
                                                        + "\"lastModified\":\"2026-08-14T10:00:00\""
                                                        + "}"
                                                        + "}"
                                        )
                        )
        );

        LimitProfileRequest request =
                LimitProfileRequest.builder()
                        .limitProfileName("Test Profile")
                        .singleTransactionValue(new BigDecimal("100000.00"))
                        .dailyTransactionVolume(10)
                        .dailyTransactionValue(new BigDecimal("500000.00"))
                        .build();

        MonnifyBaseResponse<LimitProfileResponse> result =
                limitProfileService.updateLimitProfile(
                        limitProfileCode,
                        request
                );

        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());

        assertNotNull(result.getResponseBody());

        assertEquals(
                "LP 001",
                result.getResponseBody().getLimitProfileCode()
        );

        verify(
                1,
                putRequestedFor(
                        urlPathEqualTo("/api/v1/limit-profile/LP+001")
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenLimitProfileCodeIsEmpty() {

        LimitProfileRequest request =
                LimitProfileRequest.builder()
                        .limitProfileName("Test Profile")
                        .singleTransactionValue(new BigDecimal("100000.00"))
                        .dailyTransactionVolume(10)
                        .dailyTransactionValue(new BigDecimal("500000.00"))
                        .build();

        MonnifyValidationException exception =
                assertThrows(
                        MonnifyValidationException.class,
                        () -> limitProfileService.updateLimitProfile(
                                "",
                                request
                        )
                );

        assertEquals(
                "Limit profile code is empty",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenLimitProfileCodeIsNull() {

        LimitProfileRequest request =
                LimitProfileRequest.builder()
                        .limitProfileName("Test Profile")
                        .singleTransactionValue(new BigDecimal("100000.00"))
                        .dailyTransactionVolume(10)
                        .dailyTransactionValue(new BigDecimal("500000.00"))
                        .build();

        MonnifyValidationException exception =
                assertThrows(
                        MonnifyValidationException.class,
                        () -> limitProfileService.updateLimitProfile(
                                null,
                                request
                        )
                );

        assertEquals(
                "Limit profile code is empty",
                exception.getMessage()
        );
    }

    @Test
    void shouldValidateLimitProfileName() {

        LimitProfileRequest request =
                LimitProfileRequest.builder()
                        .limitProfileName("")
                        .singleTransactionValue(new BigDecimal("100000.00"))
                        .dailyTransactionVolume(10)
                        .dailyTransactionValue(new BigDecimal("500000.00"))
                        .build();

        assertThrows(
                MonnifyValidationException.class,
                () -> limitProfileService.createLimitProfile(request)
        );
    }

    @Test
    void shouldValidateSingleTransactionValue() {

        LimitProfileRequest request =
                LimitProfileRequest.builder()
                        .limitProfileName("Test Profile")
                        .singleTransactionValue(null)
                        .dailyTransactionVolume(10)
                        .dailyTransactionValue(new BigDecimal("500000.00"))
                        .build();

        assertThrows(
                MonnifyValidationException.class,
                () -> limitProfileService.createLimitProfile(request)
        );
    }

    @Test
    void shouldValidateDailyTransactionVolume() {

        LimitProfileRequest request =
                LimitProfileRequest.builder()
                        .limitProfileName("Test Profile")
                        .singleTransactionValue(new BigDecimal("100000.00"))
                        .dailyTransactionVolume(null)
                        .dailyTransactionValue(new BigDecimal("500000.00"))
                        .build();

        assertThrows(
                MonnifyValidationException.class,
                () -> limitProfileService.createLimitProfile(request)
        );
    }

    @Test
    void shouldValidateDailyTransactionValue() {

        LimitProfileRequest request =
                LimitProfileRequest.builder()
                        .limitProfileName("Test Profile")
                        .singleTransactionValue(new BigDecimal("100000.00"))
                        .dailyTransactionVolume(10)
                        .dailyTransactionValue(null)
                        .build();

        assertThrows(
                MonnifyValidationException.class,
                () -> limitProfileService.createLimitProfile(request)
        );
    }
}