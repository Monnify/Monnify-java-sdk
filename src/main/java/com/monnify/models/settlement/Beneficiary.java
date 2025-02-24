package com.monnify.models.settlement;

import lombok.Data;

@Data
public class Beneficiary {
    private String beneficiaryType;
    private String beneficiaryCode;
    private Long beneficiaryIncome;
    private Double incurredFee;
    private String settlementStatus;
    private SettlementInformation settlementInformation;

    @Data
    public static class SettlementInformation {
        private String settledDate;
        private String settlementReference;
        private Double settlementAmount;
        private Long settlementFee;
        private String destinationAccountNumber;
        private String destinationAccountName;
        private String destinationBankName;
        private String destinationBankCode;
    }
}
