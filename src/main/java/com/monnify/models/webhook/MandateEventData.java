package com.monnify.models.webhook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MandateEventData {
    private String customerAddress;
    private LocalDateTime endDate;
    private String customerEmailAddress;
    private String customerAccountName;
    private String customerAccountNumber;
    private String customerAccountBankCode;
    private String customerName;
    private String mandateDescription;
    private String externalMandateReference;
    private String mandateStatus;
    private BigDecimal mandateAmount;
    private Boolean autoRenew;
    private String mandateCode;
    private String contractCode;
    private String customerPhoneNumber;
    private LocalDateTime startDate;
}
