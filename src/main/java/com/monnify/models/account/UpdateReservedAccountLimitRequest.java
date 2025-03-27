package com.monnify.models.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReservedAccountLimitRequest {
    @NotBlank(message = "accountReference is required")
    private String accountReference;
    @NotBlank(message = "limitProfileCode is required")
    private String limitProfileCode;
}
