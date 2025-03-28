package com.monnify.services.auth;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monnify.exceptions.MonnifyAuthenticationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.auth.AuthResponse;
import com.monnify.exceptions.MonnifyException;
import okhttp3.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.monnify.Monnify.*;

/**
 * AuthService handles authentication with the Monnify API by managing and caching access tokens.
 * It ensures that tokens are refreshed only when they expire.
 * @author Oreoluwa Somuyiwa
 */
public class AuthService {
    private static volatile long expiryTime;
    private static volatile String authToken;
    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.get("application/json");
    private static final String encodedCredentials = encodeToBase64(getApiKey(), getSecretKey());
    private static final Gson gson = new Gson();



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

        RequestBody requestBody = RequestBody.create("", JSON);
        Request.Builder requestBuilder = new Request.Builder().post(requestBody);
        requestBuilder.header("Authorization", "Basic " + encodedCredentials);

        MonnifyBaseResponse<AuthResponse> monnifyBaseResponse = null;
        TypeToken<MonnifyBaseResponse<AuthResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<AuthResponse>>() {};

        String requestUrl = getBaseUrl() + "/api/v1/auth/login";
        requestBuilder.url(requestUrl);

        try (Response response = client.newCall(requestBuilder.build()).execute()) {
            assert response.body() != null;
            String responseBody = response.body().string();
            if (!response.isSuccessful()) {
                throw new MonnifyAuthenticationException("Authentication failed: Status Code = " + response.code() + ", Response Body = " + gson.toJson(responseBody));
            }

            // Use the TypeToken provided to parse the response
            monnifyBaseResponse = gson.fromJson(responseBody, typeToken.getType());
        } catch (Exception e) {
            throw new MonnifyException("Error occurred during authentication request", e);
        }

        authToken = monnifyBaseResponse.getResponseBody().getAccessToken();
        expiryTime = System.currentTimeMillis() + (monnifyBaseResponse.getResponseBody().getExpiresIn() * 1000);
        return authToken;
    }

    private static String encodeToBase64(String apiKey, String secretKey) {
        return Base64.getEncoder().encodeToString(String.format("%s:%s", apiKey, secretKey).getBytes(StandardCharsets.UTF_8));
    }
}
