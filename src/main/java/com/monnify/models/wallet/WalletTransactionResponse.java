package com.monnify.models.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransactionResponse {
    private String walletTransactionReference;
    private String monnifyTransactionReference;
    private Double availableBalanceBefore;
    private Double availableBalanceAfter;
    private Double amount;
    private String transactionDate;
    private String transactionType;
    private String message;
    private String narration;
    private String status;
}
