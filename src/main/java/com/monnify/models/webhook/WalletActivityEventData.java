package com.monnify.models.webhook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletActivityEventData {
    private String accountType;
    private String accountName;
    private String accountNumber;
    private String accountNuban;
    private String activityType;
    private BigDecimal amount;
    private String currency;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private String reference;
    private String narration;
    private LocalDateTime activityTime;
}
