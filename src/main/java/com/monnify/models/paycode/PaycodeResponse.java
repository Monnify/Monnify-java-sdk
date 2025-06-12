package com.monnify.models.paycode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaycodeResponse {
    private String paycode;
    private String transactionReference;
    private String paycodeReference;
    private String beneficiaryName;
    private BigDecimal amount;
    private BigDecimal fee;
    private String transactionStatus;
    private LocalDateTime expiryDate;
    private LocalDateTime createdOn;
    private String createdBy;
    private String modifiedBy;
}
