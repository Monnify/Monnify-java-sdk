package com.monnify.models.transaction;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Data
public class ChargeCardRequest {
    @NotBlank(message = "transactionReference is required")
    private String transactionReference;
    @NotBlank(message = "collectionChannel is required")
    private String collectionChannel;
    @NotNull(message = "card is required")
    private CardDetails card;
    private final Map<String, String> deviceInformation = new HashMap<String, String>() {{
        put("httpBrowserLanguage", "en-US");
        put("httpBrowserJavaEnabled", "false");
        put("httpBrowserJavaScriptEnabled", "true");
        put("httpBrowserColorDepth", "24");
        put("httpBrowserScreenHeight", "1090");
        put("httpBrowserScreenWidth", "1298");
        put("httpBrowserTimeDifference", "");
        put("userAgentBrowserValue", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Mobile Safari/537.36");
    }};

    @Data
    public static class CardDetails {
        @NotBlank(message = "number is required")
        private String number;
        @NotBlank(message = "expiryMonth is required")
        private String expiryMonth;
        @NotBlank(message = "expiryYear is required")
        private String expiryYear;
        @NotBlank(message = "pin is required")
        private String pin;
        @NotBlank(message = "cvv is required")
        private String cvv;
    }
}
