package com.monnify.models.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
public class ChargeCardResponse {
    private BigDecimal authorizedAmount;
    private String paymentReference;
    private String transactionReference;
    private String status;
    private String message;
    private OtpData otpData;
    private Secure3dData secure3dData;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OtpData {
        private String id;
        private String message;
        private String authData;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Secure3dData {
        private String id;
        private String redirectUrl;
        private String callBackUrl;
        private String eciFlag;
        private String termUrl;
        private String acsUrl;
        private String transactionId;
        private String paymentId;
        private String method;
        private String jwt;
        private String md;
    }
}
