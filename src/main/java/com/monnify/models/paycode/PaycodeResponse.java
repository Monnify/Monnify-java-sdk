package com.monnify.models.paycode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    private String expiryDate;
    private String createdOn;
    private String createdBy;
    private String modifiedBy;
}
