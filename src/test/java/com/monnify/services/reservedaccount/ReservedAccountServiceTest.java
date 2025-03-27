package com.monnify.services.reservedaccount;

import com.monnify.Monnify;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.account.*;
import com.monnify.models.transaction.TransactionRecord;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.UUID;

import static com.monnify.MainTest.assertSuccess;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReservedAccountServiceTest {
    private final ReservedAccountService reservedAccountService = new ReservedAccountService();

    @BeforeAll
    static void setUp() {
        assertNotNull(System.getenv("apiKey"));
        assertNotNull(System.getenv("secretKey"));
        assertNotNull(System.getenv("contractCode"));
        Monnify.initialize(System.getenv("apiKey"), System.getenv("secretKey"));
    }

    private static final String reference = UUID.randomUUID().toString();

    @Test
    @Order(1)
    void createReservedAccount() {
        ReservedAccountRequest request = new ReservedAccountRequest();
        request.setAccountReference(reference);
        request.setAccountName("Test Reserved Account");
        request.setCurrencyCode("NGN");
        request.setContractCode(System.getenv("contractCode"));
        request.setCustomerEmail(UUID.randomUUID() + "somuyiwaoreoluwa@gmail.com");
        request.setCustomerName("Oreoluwa Somuyiwa");
        request.setBvn("21212121212");
        request.setNin("01212121212");
        request.setGetAllAvailableBanks(false);
        request.setPreferredBanks(Arrays.asList("232"));

        MonnifyBaseResponse<ReservedAccountResponse> response = reservedAccountService.createReservedAccount(request);
        assertSuccess(response);
    }

    @Test
    void createReservedAccountInvoice() {
        ReservedAccountRequest request = new ReservedAccountRequest();
        request.setAccountReference(UUID.randomUUID().toString());
        request.setAccountName("Test Reserved Account");
        request.setCurrencyCode("NGN");
        request.setContractCode(System.getenv("contractCode"));
        request.setCustomerEmail(UUID.randomUUID() + "somuyiwaoreoluwa@gmail.com");
        request.setCustomerName("Oreoluwa Somuyiwa");
        request.setBvn("21212121212");
        request.setNin("01212121212");
        request.setGetAllAvailableBanks(true);
        request.setReservedAccountType("INVOICE");

        MonnifyBaseResponse<ReservedAccountResponse> response = reservedAccountService.createReservedAccountInvoice(request);
        assertSuccess(response);
    }

    @Test
    void createReservedAccountWithLimit() {
    }

    @Test
    void updateReservedAccountLimit() {
    }

    @Test
    @Order(2)
    void getReservedAccountDetails() {
        MonnifyBaseResponse<ReservedAccountResponse> response = reservedAccountService.getReservedAccountDetails(reference);
        assertSuccess(response);
    }

    @Test
    @Order(3)
    void addLinkedAccounts() {
        MonnifyBaseResponse<ReservedAccountResponse> response = reservedAccountService.addLinkedAccounts(AddLinkedAccountsRequest.builder().getAllAvailableBanks(true).build(), reference);
        assertSuccess(response);
    }

    @Test
    @Order(5)
    void updateBVN() {
        MonnifyBaseResponse<ReservedAccountResponse> response = reservedAccountService.updateBVN(UpdateBVNRequest.builder().bvn("10293019203").build(), reference);
        assertSuccess(response);
    }

    @Test
    @Order(7)
    void deallocateReservedAccount() {
        MonnifyBaseResponse<ReservedAccountResponse> response = reservedAccountService.deallocateReservedAccount(reference);
        assertSuccess(response);
    }

    @Test
    void updateAllowedPaymentSources() {
    }

    @Test
    void updateSplitConfig() {
    }

    @Test
    @Order(4)
    void getReservedAccountTransactions() {
        MonnifyBaseResponse<SearchResponse<TransactionRecord>> response = reservedAccountService.getReservedAccountTransactions(reference, 0,10);
        assertSuccess(response);
    }

    @Test
    @Order(6)
    void updateKycInfo() {
        MonnifyBaseResponse<KycInfoResponse> response = reservedAccountService.updateKycInfo(KycInfoRequest.builder().bvn("10293019203").nin("01920393819").build(), reference);
        assertSuccess(response);
    }
}