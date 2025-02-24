package com.monnify.models.transaction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankTransferResponse {
    private String accountNumber;
    private String accountName;
    private String bankName;
    private String bankCode;
    private Long accountDurationSeconds;
    private String ussdPayment;
    private String requestTime;
    private String expiresOn;
    private String transactionReference;
    private String paymentReference;
    private BigDecimal amount;
    private BigDecimal fee;
    private BigDecimal totalPayable;
    private String collectionChannel;
    private Object productInformation;
}
