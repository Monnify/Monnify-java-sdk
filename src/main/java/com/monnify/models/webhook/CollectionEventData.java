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
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionEventData {
    private Product product;
    private String transactionReference;
    private String invoiceReference;
    private String paymentReference;
    private LocalDateTime paidOn;
    private String paymentDescription;
    private Map<String, Object> metaData;
    private List<PaymentSourceInformation> paymentSourceInformation;
    private DestinationAccountInformation destinationAccountInformation;
    private BigDecimal amountPaid;
    private BigDecimal totalPayable;
    private OfflineProductInformation offlineProductInformation;
    private CardDetails cardDetails;
    private String paymentMethod;
    private String currency;
    private BigDecimal settlementAmount;
    private String paymentStatus;
    private CustomerDTO customer;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentSourceInformation {
        private String bankCode;
        private BigDecimal amountPaid;
        private String accountName;
        private String sessionId;
        private String accountNumber;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DestinationAccountInformation {
        private String bankCode;
        private String bankName;
        private String accountNumber;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OfflineProductInformation {
        private BigDecimal amount;
        private String code;
        private String type;
    }
}
