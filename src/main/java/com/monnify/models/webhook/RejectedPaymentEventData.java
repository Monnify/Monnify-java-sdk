package com.monnify.models.webhook;

import com.monnify.models.Product;
import com.monnify.models.transaction.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RejectedPaymentEventData {
    private Product product;
    private BigDecimal amount;
    private CollectionEventData.PaymentSourceInformation paymentSourceInformation;
    private String transactionReference;
    private LocalDateTime createdOn;
    private String paymentReference;
    private PaymentRejectionInformation paymentRejectionInformation;
    private String paymentDescription;
    private CustomerDTO customer;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentRejectionInformation {
        private String bankCode;
        private String destinationAccountNumber;
        private String bankName;
        private String rejectionReason;
        private BigDecimal expectedAmount;
    }
}
