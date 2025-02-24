package com.monnify.models.disbursement;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDetails {
    private double amount;
    private String reference;
    private String narration;
    private String currency;
    private double fee;
    private boolean twoFaEnabled;
    private String status;
    private String transactionDescription;
    private String transactionReference;
    private String createdOn;
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private String destinationAccountName;
    private String destinationBankCode;
    private String destinationBankName;
}