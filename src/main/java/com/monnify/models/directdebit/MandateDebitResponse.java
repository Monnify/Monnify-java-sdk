package com.monnify.models.directdebit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MandateDebitResponse {
    private String transactionStatus;
    private String responseMessage;
    private String transactionReference;
    private String paymentReference;
    private BigDecimal debitAmount;
    private String narration;
    private String mandateCode;
}
