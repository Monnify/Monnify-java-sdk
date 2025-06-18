package com.monnify.models.directdebit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MandateStatusResponse {
    private String mandateCode;
    private String externalMandateReference;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String mandateStatus;
    private BigDecimal mandateAmount;
    private String contractCode;
    private boolean autoRenew;
    private String customerPhoneNumber;
    private String customerEmailAddress;
    private String customerAddress;
    private String customerName;
    private String customerAccountName;
    private String customerAccountNumber;
    private String customerAccountBankCode;
    private String mandateDescription;
    private BigDecimal debitAmount;
    private String authorizationMessage;
    private String authorizationLink;
}
