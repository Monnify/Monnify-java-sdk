package com.monnify.models.refund;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundResponse {
    private String refundReference;
    private String transactionReference;
    private String refundReason;
    private String customerNote;
    private BigDecimal refundAmount;
    private String refundType;
    private String refundStatus;
    private String refundStrategy;
    private String comment;
    private LocalDateTime completedOn;
    private LocalDateTime createdOn;
}
