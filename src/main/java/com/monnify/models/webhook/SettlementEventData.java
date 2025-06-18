package com.monnify.models.webhook;

import com.monnify.models.CardDetails;
import com.monnify.models.Product;
import com.monnify.models.transaction.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettlementEventData {
    private BigDecimal amount;
    private LocalDateTime settlementTime;
    private String settlementReference;
    private String destinationAccountNumber;
    private String destinationBankName;
    private String destinationAccountName;
    private Long transactionsCount;
    private List<Transaction> transactions;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Transaction {
        private Product product;
        private String transactionReference;
        private String paymentReference;
        private LocalDateTime paidOn;
        private String paymentDescription;
        private List<Account> accountPayments;
        private BigDecimal amountPaid;
        private String totalPayable;
        private Account accountDetails;
        private CardDetails cardDetails;
        private String paymentMethod;
        private String currency;
        private String paymentStatus;
        private CustomerDTO customer;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Account {
        private String bankCode;
        private BigDecimal amountPaid;
        private String accountName;
        private String accountNumber;
    }
}
