package com.monnify.models.verification;

import lombok.Data;

@Data

public class BVNVerificationResponse {
    private String bvn;
    private Name name;
    private String dateOfBirth;
    private String mobileNo;

    @Data
    public static class Name {
        private String matchStatus;
        private double matchPercentage;
    }
}
