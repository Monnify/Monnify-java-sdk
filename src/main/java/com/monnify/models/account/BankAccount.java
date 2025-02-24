package com.monnify.models.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    private String accountNumber;
    private String bankCode;
}
