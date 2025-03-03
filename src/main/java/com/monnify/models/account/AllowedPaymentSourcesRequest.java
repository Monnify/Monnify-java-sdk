package com.monnify.models.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllowedPaymentSourcesRequest {
    private boolean restrictPaymentSource;
    private AllowedPaymentSources allowedPaymentSources;
}
