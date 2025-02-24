package com.monnify.models.verification;

import lombok.Data;

@Data
public class BankValidationResponse {
    private String accountNumber;
    private String accountName;
    private String bankCode;
}