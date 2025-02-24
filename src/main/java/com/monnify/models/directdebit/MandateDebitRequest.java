package com.monnify.models.directdebit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MandateDebitRequest {
    @NotBlank(message = "paymentReference is required")
    private String paymentReference;

    @NotBlank(message = "mandateCode is required")
    private String mandateCode;

    @DecimalMin(value = "0.01", message = "debitAmount must be greater than 0")
    private BigDecimal debitAmount;

    @NotBlank(message = "narration is required")
    private String narration;

    @NotBlank(message = "customerEmail is required")
    private String customerEmail;
}
