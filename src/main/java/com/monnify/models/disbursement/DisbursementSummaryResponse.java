package com.monnify.models.disbursement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisbursementSummaryResponse {
    private BigDecimal amount;
    private String reference;
    private String narration;
    private String currency;
    private BigDecimal fee;
    private boolean twoFaEnabled;
    private String status;
    private String transactionDescription;
    private String transactionReference;
    private String createdOn;
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private String destinationAccountName;
    private String destinationBankCode;
    private String destinationBankName;
}

