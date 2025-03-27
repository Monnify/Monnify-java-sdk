package com.monnify.models.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizeOtpRequest {
    @NotBlank(message = "transactionReference is required")
    private String transactionReference;

    @NotBlank(message = "collectionChannel is required")
    private String collectionChannel;

    @NotBlank(message = "tokenId is required")
    private String tokenId;

    @NotBlank(message = "token is required")
    private String token;
}
