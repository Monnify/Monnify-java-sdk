package com.monnify.models.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomeSplitConfig {
    @NotBlank(message = "subAccountCode is required")
    private String subAccountCode;
    private double feePercentage;
    private double splitAmount;
    private double splitPercentage;
    private Boolean feeBearer;
    private String reservedAccountConfigCode;
}
