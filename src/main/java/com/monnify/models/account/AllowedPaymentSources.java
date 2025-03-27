package com.monnify.models.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllowedPaymentSources {
    private List<BankAccount> bankAccounts;
    private List<String> accountNames;
    private List<String> bvns;
}
