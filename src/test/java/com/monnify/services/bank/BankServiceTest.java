package com.monnify.services.bank;

import com.monnify.Monnify;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.bank.BankResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.monnify.MainTest.assertSuccess;
import static org.junit.jupiter.api.Assertions.*;

class BankServiceTest {
    private final BankService bankService = new BankService();

    @BeforeAll
    static void setUp() {
        assertNotNull(System.getenv("apiKey"));
        assertNotNull(System.getenv("secretKey"));
        Monnify.initialize(System.getenv("apiKey"), System.getenv("secretKey"));
    }


    @Test
    void getBanks() {
        MonnifyBaseResponse<List<BankResponse>> banks = bankService.getBanks();
        assertSuccess(banks);
        assertFalse(banks.getResponseBody().isEmpty());
    }

    @Test
    void getBanksWithUssdShortCode() {
        MonnifyBaseResponse<List<BankResponse>> banks = bankService.getBanksWithUssdShortCode();
        assertSuccess(banks);
        assertFalse(banks.getResponseBody().isEmpty());
    }
}