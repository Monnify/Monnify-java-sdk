package com.monnify.models.invoice;

import com.monnify.models.transaction.IncomeSplitConfig;
import com.monnify.models.transaction.PaymentMethod;
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
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRequest {
    @NotNull(message = "amount is required")
    @DecimalMin(value = "20.0", message = "Amount must be greater than 20")
    private BigDecimal amount;
    @NotBlank(message = "invoiceReference is required")
    private String invoiceReference;
    private String accountReference;
    @NotBlank(message = "description is required")
    private String description;
    @NotBlank(message = "currencyCode is required")
    private String currencyCode;
    @NotBlank(message = "contractCode is required")
    private String contractCode;
    @NotBlank(message = "customerEmail is required")
    private String customerEmail;
    @NotBlank(message = "customerName is required")
    private String customerName;
    @NotBlank(message = "expiryDate is required")
    private String expiryDate;
    private String redirectUrl;
    private List<PaymentMethod> paymentMethods;
    private Map<String,?> metaData;
    private List<IncomeSplitConfig> incomeSplitConfig;
}
