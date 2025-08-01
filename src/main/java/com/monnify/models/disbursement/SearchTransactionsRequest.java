package com.monnify.models.disbursement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchTransactionsRequest {
    private String sourceAccountNumber;
    private Integer pageSize;
    private Integer pageNo;
    private String startDate;
    private String endDate;
    private String amountFrom;
    private String amountTo;
}
