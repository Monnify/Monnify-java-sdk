package com.monnify.models.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletBalanceResponse {
    private BigDecimal availableBalance;
    private BigDecimal ledgerBalance;
}
