package com.monnify.models.subaccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubAccountRequest {
    @NotBlank(message = "currencyCode is required")
    private String currencyCode;

    @NotBlank(message = "bankCode is required")
    private String bankCode;

    @NotBlank(message = "accountNumber is required")
    private String accountNumber;

    @Email(message = "Invalid email format")
    @NotBlank(message = "email is required")
    private String email;

    @NotNull(message = "defaultSplitPercentage is required")
    @DecimalMin(value = "0.01", message = "defaultSplitPercentage must be greater than 0")
    private double defaultSplitPercentage;
}
