package com.monnify.models.paycode;

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
public class PaycodeRequest {
    @NotBlank(message = "beneficiaryName is required")
    private String beneficiaryName;
    @NotNull(message = "amount is required")
    @DecimalMin(value = "100.0", message = "amount must be greater than 100")
    private BigDecimal amount;
    @NotBlank(message = "paycodeReference is required")
    private String paycodeReference;
    @NotBlank(message = "expiryDate is required")
    private String expiryDate;
    @NotBlank(message = "clientId is required")
    private String clientId;
}
