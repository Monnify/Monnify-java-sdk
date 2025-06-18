package com.monnify.models.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransactionResponse {
    private String walletTransactionReference;
    private String monnifyTransactionReference;
    private BigDecimal availableBalanceBefore;
    private BigDecimal availableBalanceAfter;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String transactionType;
    private String message;
    private String narration;
    private String status;
}
