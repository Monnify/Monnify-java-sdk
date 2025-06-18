package com.monnify.models.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRecord {
    private CustomerDTO customerDTO;
    private LocalDateTime createdOn;
    private BigDecimal amount;
    private boolean flagged;
    private String currencyCode;
    private LocalDateTime completedOn;
    private String paymentDescription;
    private String paymentStatus;
    private String transactionReference;
    private String paymentReference;
    private String merchantCode;
    private String merchantName;
    private Map<String, String> metaData;
    private boolean completed;
    private List<PaymentMethod> paymentMethodList;
    private String collectionChannel;
    private BigDecimal payableAmount;
    private BigDecimal fee;
}
