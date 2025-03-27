package com.monnify.models.verification;

import lombok.Data;

@Data
public class BVNAccountMatchResponse {
    private String bvn;
    private String accountNumber;
    private String accountName;
    private String matchStatus;
    private double matchPercentage;
}