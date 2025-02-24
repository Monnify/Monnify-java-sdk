package com.monnify.models.directdebit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MandateResponse {
    private String responseMessage;
    private String mandateReference;
    private String mandateCode;
    private String mandateStatus;
}
