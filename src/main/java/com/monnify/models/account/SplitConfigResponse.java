package com.monnify.models.account;

import com.monnify.models.transaction.IncomeSplitConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SplitConfigResponse {
    private String code;
    private String reservedAccountCode;
    private String feeBearer;
    private List<IncomeSplitConfig> configDetails;
}
