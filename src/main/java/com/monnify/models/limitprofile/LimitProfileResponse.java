package com.monnify.models.limitprofile;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LimitProfileResponse {
    private String limitProfileCode;
    private String limitProfileName;
    private BigDecimal singleTransactionValue;
    private long dailyTransactionVolume;
    private BigDecimal dailyTransactionValue;
    private LocalDateTime dateCreated;
    private LocalDateTime lastModified;
}