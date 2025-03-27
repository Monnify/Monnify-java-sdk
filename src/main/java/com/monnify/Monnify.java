package com.monnify;

import com.monnify.exceptions.DuplicateInitializationException;
import com.monnify.exceptions.MonnifyException;
import com.monnify.utils.StringUtils;

import java.util.Objects;

/**
 * Monnify SDK Wrapper for handling authentication and API configuration.
 * This class is designed to be initialized once with API credentials and provides access to the API key, secret key, and base URL.
 * @author Oreoluwa Somuyiwa
 * @version 1.0.0
 */
public final class Monnify {
    private static volatile boolean initialized = false;
    private static String API_KEY;
    private static String SECRET_KEY;
    private static String BASE_URL;

    private Monnify() {
        // Private constructor to prevent instantiation
    }

    /**
     * Initializes the Monnify SDK with the provided API key and secret key.
     * This method can only be called once; subsequent calls will throw an exception.
     *
     * @param apiKey    The API key obtained from the Monnify dashboard.
     * @param secretKey The secret key associated with the API key.
     * @throws MonnifyException if Monnify has already been initialized or if keys are invalid.
     */
    public static synchronized void initialize(String apiKey, String secretKey) {
        if (initialized) {
            throw new DuplicateInitializationException("Monnify has already been initialized. API keys cannot be changed.");
        }

        API_KEY = checkAPIKey(apiKey);
        SECRET_KEY = Objects.requireNonNull(secretKey, "Secret Key cannot be null");
        BASE_URL = API_KEY.startsWith("MK_PROD") ? "https://api.monnify.com" : "https://sandbox.monnify.com";

        initialized = true;
    }

    private static String checkAPIKey(String apiKey) {
        if (apiKey == null) {
            throw new MonnifyException("No API key provided. Please set your API key. You can access your API keys from the Monnify Dashboard.");
        } else if (apiKey.isEmpty()) {
            throw new MonnifyException("Your API key is invalid, as it is an empty string. You can double-check your API key from the Monnify Dashboard.");
        } else if (StringUtils.containsWhitespace(apiKey)) {
            throw new MonnifyException("Your API key is invalid, as it contains whitespace. You can double-check your API key from the Monnify Dashboard.");
        } else if (!apiKey.startsWith("MK")) {
            throw new MonnifyException("Your API key is invalid, as it does not begin with MK. You can double-check your API key from the Monnify Dashboard.");
        }
        return apiKey;
    }

    public static String getApiKey() {
        ensureInitialized();
        return API_KEY;
    }

    public static String getSecretKey() {
        ensureInitialized();
        return SECRET_KEY;
    }

    public static String getBaseUrl() {
        ensureInitialized();
        return BASE_URL;
    }

    private static void ensureInitialized() {
        if (!initialized) {
            throw new MonnifyException("Monnify has not been initialized. Call Monnify.initialize(apiKey, secretKey) before using it.");
        }
    }
}

