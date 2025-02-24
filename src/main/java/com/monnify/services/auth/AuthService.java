package com.monnify.services.auth;

import com.google.gson.reflect.TypeToken;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.auth.AuthResponse;
import com.monnify.utils.MonnifyClient;
import com.monnify.exceptions.MonnifyException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.monnify.Monnify.getApiKey;
import static com.monnify.Monnify.getSecretKey;

/**
 * AuthService handles authentication with the Monnify API by managing and caching access tokens.
 * It ensures that tokens are refreshed only when they expire.
 * @author Oreoluwa Somuyiwa
 */
public class AuthService {
    private static volatile long expiryTime;
    private static volatile String authToken;
    private static final MonnifyClient monnifyClient = new MonnifyClient();
    private static final String encodedCredentials = encodeToBase64(getApiKey(), getSecretKey());

    /**
     * Retrieves a valid authentication token. If the cached token is still valid,
     * it returns the cached value. Otherwise, it fetches a new token from Monnify.
     *
     * @return A valid authentication token.
     * @throws MonnifyException If the authentication request fails.
     * @author Oreoluwa Somuyiwa
     */
    public static synchronized String getToken() {
        if (expiryTime > System.currentTimeMillis() && authToken != null) {
            return authToken;
        }

        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization", "Basic " + encodedCredentials);

        TypeToken<MonnifyBaseResponse<AuthResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<AuthResponse>>() {};

        MonnifyBaseResponse<AuthResponse> response = monnifyClient.post(
                "/api/v1/auth/login",
                "",
                headers,
                null,
                typeToken
        );

        authToken = response.getResponseBody().getAccessToken();
        expiryTime = System.currentTimeMillis() + (response.getResponseBody().getExpiresIn() * 1000);

        return authToken;
    }

    private static String encodeToBase64(String apiKey, String secretKey) {
        return Base64.getEncoder().encodeToString(String.format("%s:%s", apiKey, secretKey).getBytes(StandardCharsets.UTF_8));
    }
}
