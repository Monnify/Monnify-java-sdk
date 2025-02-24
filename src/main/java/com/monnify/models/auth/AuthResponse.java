package com.monnify.models.auth;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private Long expiresIn;
}

