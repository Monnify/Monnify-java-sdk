package com.monnify.models.subaccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSubAccountRequest {
    private String subAccountCode;
    private String currencyCode;
    private String bankCode;
    private String accountNumber;
    private String email;
    private Double defaultSplitPercentage;
}