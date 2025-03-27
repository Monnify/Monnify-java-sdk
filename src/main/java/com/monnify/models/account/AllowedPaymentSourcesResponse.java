package com.monnify.models.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllowedPaymentSourcesResponse {
    private boolean restrictPaymentSource;
    private AllowedPaymentSources allowedPaymentSources;
}
