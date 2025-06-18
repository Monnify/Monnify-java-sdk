package com.monnify.services.transaction;

import com.monnify.Monnify;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.transaction.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.*;

import static com.monnify.MainTest.assertSuccess;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransactionServiceTest {
    private final TransactionService transactionService = new TransactionService();
    @BeforeAll
    static void setUp() {
        assertNotNull(System.getenv("apiKey"));
        assertNotNull(System.getenv("secretKey"));
        assertNotNull(System.getenv("contractCode"));
        Monnify.initialize(System.getenv("apiKey"), System.getenv("secretKey"));
    }

    private static final String reference = UUID.randomUUID().toString();
    private static String transactionReference = "";
    private static String tokenId = "";

    @Test
    @Order(1)
    void initializeTransaction() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(BigDecimal.valueOf(20L));
        transactionRequest.setCustomerName("Stephen Ikhane");
        transactionRequest.setCustomerEmail("stephen@ikhane.com");
        transactionRequest.setPaymentReference(reference);
        transactionRequest.setPaymentDescription("Trial transaction");
        transactionRequest.setCurrencyCode("NGN");
        transactionRequest.setContractCode(System.getenv("contractCode"));
        transactionRequest.setRedirectUrl("https://my-merchants-page.com/transaction/confirm");
        transactionRequest.setPaymentMethods(Arrays.asList(PaymentMethod.CARD, PaymentMethod.ACCOUNT_TRANSFER));

        IncomeSplitConfig incomeSplitConfig = new IncomeSplitConfig();
        incomeSplitConfig.setSubAccountCode("MFY_SUB_523071569011");
        incomeSplitConfig.setSplitAmount(0.0);
        incomeSplitConfig.setFeeBearer(false);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("name", "Ore Sho");
        metadata.put("Balance", 100000);
        metadata.put("CurrencyCode", "NGN");
        metadata.put("MerchantName", "Stephen Ikhane");
        transactionRequest.setMetaData(metadata);

        transactionRequest.setIncomeSplitConfig(Collections.singletonList(incomeSplitConfig));
        TransactionService transactionService = new TransactionService();
        MonnifyBaseResponse<TransactionResponse> response = transactionService.initializeTransaction(transactionRequest);
        assertSuccess(response);
        transactionReference = response.getResponseBody().getTransactionReference();
    }

    @Test
    @Order(2)
    void payWithBankTransfer() {
        MonnifyBaseResponse<BankTransferResponse> response = transactionService.payWithBankTransfer(
                new BankTransferRequest(transactionReference, "058"));
        assertSuccess(response);
    }

    @Test
    @Order(2)
    void chargeCard() {
        ChargeCardRequest.CardDetails cardDetails = new ChargeCardRequest.CardDetails();
        cardDetails.setNumber("4000000000000002");
        cardDetails.setExpiryMonth("10");
        cardDetails.setExpiryYear("2022");
        cardDetails.setPin("1234");
        cardDetails.setCvv("123");

        ChargeCardRequest chargeCardRequest = new ChargeCardRequest();
        chargeCardRequest.setTransactionReference(transactionReference);
        chargeCardRequest.setCollectionChannel("API_NOTIFICATION");
        chargeCardRequest.setCard(cardDetails);

        MonnifyBaseResponse<ChargeCardResponse> response = transactionService.chargeCard(chargeCardRequest);
        tokenId = response.getResponseBody().getSecure3dData().getId();
        assertSuccess(response);
    }

    @Test
    void chargeCardToken() {
    }

    @Test
    @Order(3)
    void authorizeCardOtp() {
        AuthorizeOtpRequest authorizeOtpRequest = new AuthorizeOtpRequest();
        authorizeOtpRequest.setTransactionReference(transactionReference);
        authorizeOtpRequest.setCollectionChannel("API_NOTIFICATION");
        authorizeOtpRequest.setTokenId(tokenId);
        authorizeOtpRequest.setToken("123456");

        MonnifyBaseResponse<ChargeCardResponse> response = transactionService.authorizeCardOtp(authorizeOtpRequest);
        assertSuccess(response);
    }

    @Test
    void searchTransactions() {
        MonnifyBaseResponse<SearchResponse<TransactionRecord>> response = transactionService.searchTransactions(
                0, 10, null, null, null, null, null, null, null, "PAID", null, null
        );
        assertSuccess(response);
    }

    @Test
    @Order(3)
    void getStatus() {
        MonnifyBaseResponse<TransactionStatusResponse> response = transactionService.getStatus(transactionReference);
        assertSuccess(response);
    }

    @Test
    void getStatusByReference() {
        MonnifyBaseResponse<TransactionStatusResponse> response = transactionService
                .getStatusByReference("3ac2af37-8d45-4936-8e7c-d717bd1dd90d",null);
        assertSuccess(response);
    }
}