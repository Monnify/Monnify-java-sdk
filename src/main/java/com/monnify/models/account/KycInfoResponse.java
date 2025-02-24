package com.monnify.models.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KycInfoResponse {
    private String accountReference;
    private String accountName;
    private String customerEmail;
    private String customerName;
    private String bvn;
}

