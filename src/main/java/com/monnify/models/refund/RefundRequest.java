package com.monnify.models.refund;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundRequest {

    @NotBlank(message = "transactionReference is required")
    private String transactionReference;

    @NotBlank(message = "refundReference is required")
    private String refundReference;

    @NotNull(message = "refundAmount is required")
    private BigDecimal refundAmount;

    @NotBlank(message = "refundReason is required")
    private String refundReason;

    @NotBlank(message = "customerNote reason is required")
    private String customerNote;

    private String destinationAccountNumber;

    private String destinationAccountBankCode;
}
