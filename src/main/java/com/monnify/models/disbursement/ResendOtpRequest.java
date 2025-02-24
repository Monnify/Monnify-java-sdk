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
public class ResendOtpRequest {
    @NotBlank(message = "reference is required")
    private String reference;
}
