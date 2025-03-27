package com.monnify.models.subaccount;

import lombok.Data;

@Data
public class SubAccountResponse {
    private String subAccountCode;
    private String accountNumber;
    private String accountName;
    private String currencyCode;
    private String email;
    private String bankCode;
    private String bankName;
    private Double defaultSplitPercentage;
    private String settlementProfileCode;
    private String[] settlementReportEmails;
}
