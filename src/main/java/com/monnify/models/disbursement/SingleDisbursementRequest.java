package com.monnify.models.disbursement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SingleDisbursementRequest {
    @NotNull(message = "amount is required")
    @DecimalMin(value = "0.01", message = "amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "reference is required")
    private String reference;

    @NotBlank(message = "narration is required")
    private String narration;

    @NotBlank(message = "destinationBankCode is required")
    private String destinationBankCode;

    @NotBlank(message = "destinationAccountNumber is required")
    private String destinationAccountNumber;

    @NotBlank(message = "currency is required")
    private String currency;

    @NotBlank(message = "sourceAccountNumber is required")
    private String sourceAccountNumber;

    private Boolean async;
}
