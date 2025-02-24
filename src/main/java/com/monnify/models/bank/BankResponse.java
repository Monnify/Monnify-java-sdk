package com.monnify.models.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankResponse {
    public String code;
    private String name;
    private String ussdTemplate;
    private String baseUssdCode;
    private String transferUssdTemplate;
}
