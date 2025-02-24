package com.monnify.models.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllowedPaymentSourcesRequest {
    private boolean restrictPaymentSource;
    private AllowedPaymentSources allowedPaymentSources;
}
