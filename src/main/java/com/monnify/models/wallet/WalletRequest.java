package com.monnify.models.wallet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

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
        @NotBlank(message = "bvn is required")
        @Pattern(regexp = "\\d{11}", message = "BVN must be an 11-digit number")
        private String bvn;
        @Past(message = "bvnDateOfBirth must be in the past")
        @NotNull(message = "bvnDateOfBirth is required")
        private LocalDate bvnDateOfBirth;
    }
}
