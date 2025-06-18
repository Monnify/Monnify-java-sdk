package com.monnify.models.webhook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisbursementEventData {
    private BigDecimal amount;
    private String transactionReference;
    private BigDecimal fee;
    private String transactionDescription;
    private String destinationAccountNumber;
    private String sessionId;
    private LocalDateTime createdOn;
    private String destinationAccountName;
    private String reference;
    private String destinationBankCode;
    private LocalDateTime completedOn;
    private String narration;
    private String currency;
    private String destinationBankName;
    private String status;
}
