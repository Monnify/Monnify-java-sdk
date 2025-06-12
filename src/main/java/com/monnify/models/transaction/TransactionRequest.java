package com.monnify.models.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    @NotNull(message = "amount is required")
    @DecimalMin(value = "20.0", message = "Amount must be greater than 20")
    private BigDecimal amount;
    private String customerName;
    @NotBlank(message = "customerEmail is required")
    private String customerEmail;
    @NotBlank(message = "paymentReference is required")
    private String paymentReference;
    private String paymentDescription;
    @NotBlank(message = "currencyCode is required")
    private String currencyCode;
    @NotBlank(message = "contractCode is required")
    private String contractCode;
    private String redirectUrl;
    private List<PaymentMethod> paymentMethods;
    private Map<String, Object> metaData;
    private List<IncomeSplitConfig> incomeSplitConfig;
}
