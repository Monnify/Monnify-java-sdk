package com.monnify.models.invoice;

import com.monnify.models.transaction.IncomeSplitConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse {
    private BigDecimal amount;
    private String invoiceReference;
    private String invoiceStatus;
    private String description;
    private String contractCode;
    private String customerEmail;
    private String customerName;
    private LocalDateTime expiryDate;
    private String createdBy;
    private LocalDateTime createdOn;
    private String checkoutUrl;
    private String accountNumber;
    private String accountName;
    private String bankName;
    private String bankCode;
    private String redirectUrl;
    private String transactionReference;
    private List<IncomeSplitConfig> incomeSplitConfig;
}
