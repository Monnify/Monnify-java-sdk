package com.monnify.models.limitprofile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LimitProfileRequest {
    private String limitProfileName;
    private BigDecimal singleTransactionValue;
    private Integer dailyTransactionVolume;
    private BigDecimal dailyTransactionValue;
}