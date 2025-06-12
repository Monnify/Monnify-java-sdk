package com.monnify.models.transaction;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class BankTransferResponse {
    private String accountNumber;
    private String accountName;
    private String bankName;
    private String bankCode;
    private long accountDurationSeconds;
    private String ussdPayment;
    private String requestTime;
    private LocalDateTime expiresOn;
    private String transactionReference;
    private String paymentReference;
    private BigDecimal amount;
    private BigDecimal fee;
    private BigDecimal totalPayable;
    private String collectionChannel;
    private Map<String, String> productInformation;
}
