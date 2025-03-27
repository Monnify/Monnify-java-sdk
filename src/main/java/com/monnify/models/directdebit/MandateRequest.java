package com.monnify.models.directdebit;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MandateRequest {

    @NotBlank(message = "contractCode is required")
    private String contractCode;

    @NotBlank(message = "mandateReference is required")
    private String mandateReference;

    private Boolean autoRenew;
    private Boolean customerCancellation;

    @NotBlank(message = "customerName is required")
    private String customerName;

    @NotBlank(message = "customerPhoneNumber is required")
    private String customerPhoneNumber;

    @NotBlank(message = "customerEmailAddress is required")
    private String customerEmailAddress;

    @NotBlank(message = "customerAddress is required")
    private String customerAddress;

    private String customerAccountName;

    @NotBlank(message = "customerAccountNumber is required")
    private String customerAccountNumber;

    @NotBlank(message = "customerAccountBankCode is required")
    private String customerAccountBankCode;

    @NotBlank(message = "mandateDescription is required")
    private String mandateDescription;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$",
            message = "mandateStartDate must be in the format yyyy-MM-dd'T'HH:mm:ss")
    @NotBlank(message = "mandateStartDate is required")
    private String mandateStartDate;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$",
            message = "mandateEndDate must be in the format yyyy-MM-dd'T'HH:mm:ss")
    @NotBlank(message = "mandateEndDate is required")
    private String mandateEndDate;

    @DecimalMin(value = "0.01", message = "mandateAmount must be greater than 0") //todo: confirm minimum
    private BigDecimal mandateAmount;

    @DecimalMin(value = "0.01", message = "debitAmount must be greater than 0")
    private BigDecimal debitAmount;
}
