package com.monnify.models.verification;

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
public class BVNVerificationRequest {
    @NotBlank(message = "bvn is required")
    @Pattern(regexp = "\\d{11}", message = "BVN must be an 11-digit number")
    private String bvn;
    @NotBlank(message = "name is required")
    private String name;
    @Past(message = "dateOfBirth must be in the past")
    @NotNull(message = "dateOfBirth is required")
    private LocalDate dateOfBirth;
    @NotBlank(message = "mobileNo is required")
    @Pattern(regexp = "\\d{11}", message = "mobileNo must be an 11-digit number")
    private String mobileNo;
}
