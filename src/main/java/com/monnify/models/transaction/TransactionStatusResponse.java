package com.monnify.models.transaction;

import com.monnify.models.CardDetails;
import com.monnify.models.Product;
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
public class TransactionStatusResponse {
    private String transactionReference;
    private String paymentReference;
    private BigDecimal amountPaid;
    private BigDecimal totalPayable;
    private BigDecimal settlementAmount;
    private LocalDateTime paidOn;
    private String paymentStatus;
    private String paymentDescription;
    private String transactionHash;
    private String currency;
    private String paymentMethod;
    private Product product;
    private CardDetails cardDetails;
    private CustomerDTO customer;
    private Map<String, Object> metaData;
    private List<Map<String, Object>> accountPayments;
}
