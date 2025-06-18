package com.monnify.models.disbursement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkDisbursementSummary {
    private String title;
    private BigDecimal totalAmount;
    private BigDecimal totalFee;
    private String batchReference;
    private long totalTransactions;
    private long failedCount;
    private long successfulCount;
    private long pendingCount;
    private String batchStatus;
    private LocalDateTime dateCreated;
}
