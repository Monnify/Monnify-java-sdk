package com.monnify.models.disbursement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateOtpRequest {
    @NotBlank(message = "reference is required")
    private String reference;

    @NotBlank(message = "authorizationCode is required")
    private String authorizationCode;
}
