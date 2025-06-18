package com.monnify.models.account;

import com.monnify.models.transaction.IncomeSplitConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservedAccountRequest {
    @NotBlank(message = "accountReference is required")
    private String accountReference;
    private String accountName;
    @NotBlank(message = "currencyCode is required")
    private String currencyCode;
    @NotBlank(message = "contractCode is required")
    private String contractCode;
    @NotBlank(message = "customerEmail is required")
    @Email(message = "Please use a valid email address")
    private String customerEmail;
    private String customerName;
    @Pattern(regexp = "\\d{11}", message = "BVN must be an 11-digit number")
    private String bvn;
    @Pattern(regexp = "\\d{11}", message = "NIN must be an 11-digit number")
    private String nin;
    private String reservedAccountType;
    @Builder.Default
    private boolean getAllAvailableBanks = true;
    @Builder.Default
    private List<String> preferredBanks = new ArrayList<>();
    private List<IncomeSplitConfig> incomeSplitConfig;
    private Boolean restrictPaymentSource;
    private AllowedPaymentSources allowedPaymentSources;
    private String limitProfileCode;
}
