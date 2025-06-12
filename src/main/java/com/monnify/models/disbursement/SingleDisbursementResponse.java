package com.monnify.models.disbursement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleDisbursementResponse {
    private BigDecimal amount;
    private String reference;
    private String status;
    private LocalDateTime dateCreated;
    private BigDecimal totalFee;
    private String destinationAccountName;
    private String destinationBankName;
    private String destinationAccountNumber;
    private String destinationBankCode;
}
