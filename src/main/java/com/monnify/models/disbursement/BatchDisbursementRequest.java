package com.monnify.models.disbursement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchDisbursementRequest {
    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "batchReference is required")
    private String batchReference;

    @NotBlank(message = "narration is required")
    private String narration;

    @NotBlank(message = "sourceAccountNumber is required")
    private String sourceAccountNumber;

    @NotBlank(message = "onValidationFailure is required")
    private String onValidationFailure;

    private Integer notificationInterval;

    @NotEmpty(message = "transactionList must contain at least one transaction")
    private List<DisbursementTransaction> transactionList;
}
