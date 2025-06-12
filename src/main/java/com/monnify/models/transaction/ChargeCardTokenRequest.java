package com.monnify.models.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargeCardTokenRequest {

    @NotBlank(message = "cardToken is required")
    private String cardToken;

    @DecimalMin(value = "20.0", message = "Amount must be greater than 20")
    private BigDecimal amount;

    @NotBlank(message = "customerName is required")
    private String customerName;

    @NotBlank(message = "customerEmail is required")
    private String customerEmail;

    @NotBlank(message = "paymentReference is required")
    private String paymentReference;

    @NotBlank(message = "paymentDescription is required")
    private String paymentDescription;

    @NotBlank(message = "currencyCode is required")
    private String currencyCode;

    @NotBlank(message = "contractCode is required")
    private String contractCode;

    @NotBlank(message = "apiKey is required")
    private String apiKey;

    private Map<String, Object> metaData;
}
