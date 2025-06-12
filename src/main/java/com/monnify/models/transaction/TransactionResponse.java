package com.monnify.models.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String transactionReference;
    private String paymentReference;
    private String merchantName;
    private String apiKey;
    private List<String> enabledPaymentMethod;
    private String checkoutUrl;
    private List<IncomeSplitConfig> incomeSplitConfig;
    private Map<String, Object> metaData;
}
