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
public class MandateStatusResponse {
    private String mandateCode;
    private String externalMandateReference;
    private String startDate;
    private String endDate;
    private String mandateStatus;
    private BigDecimal mandateAmount;
    private String contractCode;
    private Boolean autoRenew;
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
