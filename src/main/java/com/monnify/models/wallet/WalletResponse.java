package com.monnify.models.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse {
    private String walletName;
    private String walletReference;
    private String customerName;
    private String customerEmail;
    private String feeBearer;
    private WalletRequest.BvnDetails bvnDetails;
    private String accountNumber;
    private String accountName;
    private TopUpAccountDetails topUpAccountDetails;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TopUpAccountDetails {
        private String accountNumber;
        private String accountName;
        private String bankCode;
        private String bankName;
    }
}
