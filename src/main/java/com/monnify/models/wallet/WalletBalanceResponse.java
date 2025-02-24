package com.monnify.models.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletBalanceResponse {
    private Double availableBalance;
    private Double ledgerBalance;
}
