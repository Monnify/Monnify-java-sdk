package com.monnify.models.transaction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChargeCardResponse {
    private BigDecimal authorizedAmount;
    private String paymentReference;
    private String transactionReference;
    private String status;
    private String message;
}
