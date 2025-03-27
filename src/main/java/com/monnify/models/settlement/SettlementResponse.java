package com.monnify.models.settlement;

import com.monnify.models.transaction.TransactionStatusResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettlementResponse {
    private TransactionStatusResponse transaction;
    private List<Beneficiary> beneficiaries;
}
