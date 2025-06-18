package com.monnify.models.webhook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundEventData {
    private String merchantReason;
    private String transactionReference;
    private LocalDateTime completedOn;
    private String refundStatus;
    private String customerNote;
    private LocalDateTime createdOn;
    private String refundReference;
    private BigDecimal refundAmount;
}
