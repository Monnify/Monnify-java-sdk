package com.monnify.models.limitprofile;

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
public class LimitProfileRequest {
    @NotBlank(message = "Limit profile name is required")
    private String limitProfileName;
    @NotNull(message = "Single transaction value is required")
    private BigDecimal singleTransactionValue;
    @NotNull(message = "Daily transaction volume is required")
    private Integer dailyTransactionVolume;
    @NotNull(message = "Daily transaction value is required")
    private BigDecimal dailyTransactionValue;
}