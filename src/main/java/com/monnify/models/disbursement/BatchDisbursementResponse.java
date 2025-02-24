package com.monnify.models.disbursement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchDisbursementResponse {
    private BigDecimal totalAmount;
    private BigDecimal totalFee;
    private String batchReference;
    private String batchStatus;
    private Integer totalTransactionsCount;
    private String dateCreated;
}

