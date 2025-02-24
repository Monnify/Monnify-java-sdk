package com.monnify.models.verification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BVNVerificationRequest {
    @Pattern(regexp = "\\d{11}", message = "Must be an 11-digit number")
    private String bvn;
    private String name;
    private String dateOfBirth;
    private String mobileNo;
}
