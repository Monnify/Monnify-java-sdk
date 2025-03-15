package com.monnify.models.disbursement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkDisbursementSummary {
    private String title;
    private double totalAmount;
    private double totalFee;
    private String batchReference;
    private long totalTransactions;
    private long failedCount;
    private long successfulCount;
    private long pendingCount;
    private String batchStatus;
    private String dateCreated;
}
