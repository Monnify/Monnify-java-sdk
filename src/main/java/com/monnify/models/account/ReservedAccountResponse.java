package com.monnify.models.account;

import com.monnify.models.limitprofile.LimitProfileResponse;
import com.monnify.models.transaction.IncomeSplitConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservedAccountResponse {
    private String contractCode;
    private String accountReference;
    private String accountName;
    private String currencyCode;
    private String customerEmail;
    private String customerName;
    private List<Account> accounts;
    private String collectionChannel;
    private String reservationReference;
    private String reservedAccountType;
    private String status;
    private String createdOn;
    private Contract contract;
    private List<IncomeSplitConfig> incomeSplitConfig;
    private String bvn;
    private String nin;
    private boolean restrictPaymentSource;
    private LimitProfileResponse limitProfileConfig;
}
