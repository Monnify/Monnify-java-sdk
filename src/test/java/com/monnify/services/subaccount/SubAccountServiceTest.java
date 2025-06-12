package com.monnify.services.subaccount;

import com.monnify.Monnify;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.subaccount.SubAccountRequest;
import com.monnify.models.subaccount.SubAccountResponse;
import com.monnify.models.subaccount.UpdateSubAccountRequest;
import org.junit.jupiter.api.*;

import java.util.Collections;
import java.util.List;

import static com.monnify.MainTest.assertSuccess;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SubAccountServiceTest {
    private final SubAccountService subAccountService = new SubAccountService();
    @BeforeAll
    static void setUp() {
        assertNotNull(System.getenv("apiKey"));
        assertNotNull(System.getenv("secretKey"));
        assertNotNull(System.getenv("contractCode"));
        Monnify.initialize(System.getenv("apiKey"), System.getenv("secretKey"));
    }

    private static String subAccountCode = "";
    private static final SubAccountRequest subAccountRequest = SubAccountRequest
            .builder()
            .accountNumber("0104430292")
            .bankCode("044")
            .currencyCode("NGN")
            .email("johndoes@example.com")
            .defaultSplitPercentage(12)
            .build();

    @Test
    @Order(1)
    void createSubAccounts() {
        MonnifyBaseResponse<List<SubAccountResponse>> response = subAccountService.createSubAccounts(Collections.singletonList(subAccountRequest));
        assertSuccess(response);
        subAccountCode = response.getResponseBody().get(0).getSubAccountCode();
    }

    @Test
    @Order(2)
    void getSubAccounts() {
        MonnifyBaseResponse<List<SubAccountResponse>> response = subAccountService.getSubAccounts();
        assertSuccess(response);
    }

    @Test
    @Order(3)
    void updateSubAccount() {
        MonnifyBaseResponse<SubAccountResponse> response =
                subAccountService.updateSubAccount(UpdateSubAccountRequest.builder()
                                .accountNumber("9067513464")
                                .email("johndoes@gmail.com")
                                .bankCode("50515")
                        .subAccountCode(subAccountCode).defaultSplitPercentage(30.0).build());
        assertSuccess(response);
        assertEquals(30, response.getResponseBody().getDefaultSplitPercentage());
    }

    @Test
    @Order(4)
    void deleteSubAccount() {
        MonnifyBaseResponse<Void> response = subAccountService.deleteSubAccount(subAccountCode);
        assertSuccess(response);
    }
}