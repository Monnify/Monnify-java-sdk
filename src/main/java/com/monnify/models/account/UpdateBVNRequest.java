package com.monnify.models.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBVNRequest {
    @Pattern(regexp = "\\d{11}", message = "BVN must be an 11-digit number")
    @NotBlank(message = "bvn is required")
    private String bvn;
}
