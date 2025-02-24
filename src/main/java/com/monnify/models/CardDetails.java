package com.monnify.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDetails {
    private String cardType;
    private String last4;
    private String expMonth;
    private String expYear;
    private String bin;
    private String bankCode;
    private String bankName;
    private boolean reusable;
    private String countryCode;
    private String cardToken;
    private boolean supportsTokenization;
    private String maskedPan;
}
