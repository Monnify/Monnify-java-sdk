package com.monnify.models.verification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NINVerificationRequest {
    @NotBlank(message = "nin is required")
    private String nin;
}
