package com.monnify.models.wallet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletRequest {
    @NotBlank(message = "walletReference is required")
    private String walletReference;
    @NotBlank(message = "walletName is required")
    private String walletName;
    @NotBlank(message = "customerName is required")
    private String customerName;
    @NotNull(message = "bvnDetails is required")
    private BvnDetails bvnDetails;
    @NotBlank(message = "customerEmail is required")
    private String customerEmail;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BvnDetails {
        private String bvn;
        private String bvnDateOfBirth;
    }
}
