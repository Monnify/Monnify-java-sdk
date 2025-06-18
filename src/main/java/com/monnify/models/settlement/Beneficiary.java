package com.monnify.models.settlement;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Beneficiary {
    private String beneficiaryType;
    private String beneficiaryCode;
    private BigDecimal beneficiaryIncome;
    private BigDecimal incurredFee;
    private String settlementStatus;
    private SettlementInformation settlementInformation;

    @Data
    public static class SettlementInformation {
        private LocalDateTime settledDate;
        private String settlementReference;
        private BigDecimal settlementAmount;
        private BigDecimal settlementFee;
        private String destinationAccountNumber;
        private String destinationAccountName;
        private String destinationBankName;
        private String destinationBankCode;
    }
}
