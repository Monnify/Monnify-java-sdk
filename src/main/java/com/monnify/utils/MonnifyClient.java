package com.monnify.utils;


import com.google.gson.Gson;
import com.monnify.exceptions.MonnifyException;
import com.monnify.models.MonnifyBaseResponse;
import okhttp3.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.monnify.Monnify.getBaseUrl;

public class MonnifyClient {
    private final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.get("application/json");
    private static final Logger logger = LoggerFactory.getLogger(MonnifyClient.class);
    private static final Gson gson = new Gson();

    public <T> MonnifyBaseResponse<T> post(String path, String body, Map<String, String> headers, Map<String, String> parameters, TypeToken<MonnifyBaseResponse<T>> typeToken) {
        // Create Request Body
        RequestBody requestBody = RequestBody.create(body, JSON);

        // Build headers
        Request.Builder requestBuilder = new Request.Builder().post(requestBody);
        return buildRequest(requestBuilder, path, headers, parameters, typeToken);
    }

    public <T> MonnifyBaseResponse<T> get(String path, Map<String, String> headers, Map<String, String> parameters, TypeToken<MonnifyBaseResponse<T>> typeToken) {
        // Build headers
        Request.Builder requestBuilder = new Request.Builder().get();
        return buildRequest(requestBuilder, path, headers, parameters, typeToken);
    }

    public <T> MonnifyBaseResponse<T> put(String path, String body, Map<String, String> headers, Map<String, String> parameters, TypeToken<MonnifyBaseResponse<T>> typeToken) {
        // Create Request Body
        RequestBody requestBody = RequestBody.create(body, JSON);

        // Build headers
        Request.Builder requestBuilder = new Request.Builder().put(requestBody);
        return buildRequest(requestBuilder, path, headers, parameters, typeToken);
    }

    public <T> MonnifyBaseResponse<T> patch(String path, String body, Map<String, String> headers, Map<String, String> parameters, TypeToken<MonnifyBaseResponse<T>> typeToken) {
        // Create Request Body
        RequestBody requestBody = RequestBody.create(body, JSON);

        // Build headers
        Request.Builder requestBuilder = new Request.Builder().patch(requestBody);
        return buildRequest(requestBuilder, path, headers, parameters, typeToken);
    }

    public <T> MonnifyBaseResponse<T> delete(String path, String body, Map<String, String> headers, Map<String, String> parameters, TypeToken<MonnifyBaseResponse<T>> typeToken) {
        // Create Request Body
        RequestBody requestBody = RequestBody.create(body, JSON);

        // Build headers
        Request.Builder requestBuilder = new Request.Builder().delete(requestBody);
        return buildRequest(requestBuilder, path, headers, parameters, typeToken);
    }

    private <T> MonnifyBaseResponse<T> buildRequest(Request.Builder requestBuilder, String path, Map<String, String> headers, Map<String, String> parameters, TypeToken<MonnifyBaseResponse<T>> typeToken) {
        // Build header
        if (headers != null) {
            headers.forEach(requestBuilder::addHeader);
        }

        // Build query parameters
        String requestUrl = getBaseUrl() + path + buildQueryString(parameters);
        requestBuilder.url(requestUrl);

        // Send request and handle response
        MonnifyBaseResponse<T> monnifyBaseResponse = null;

        try (Response response = client.newCall(requestBuilder.build()).execute()) {
            assert response.body() != null;
            String responseBody = response.body().string();
            if (!response.isSuccessful()) {
                logger.error("Request failed: Status Code = {}, Response Body = {}", response.code(), responseBody);
                monnifyBaseResponse = gson.fromJson(responseBody, typeToken.getType());
                return monnifyBaseResponse;
            }

            // Use the TypeToken provided to parse the response
            monnifyBaseResponse = gson.fromJson(responseBody, typeToken.getType());
        } catch (Exception e) {
            throw new MonnifyException("Error occurred during request : " + requestUrl, e);
        }

        return monnifyBaseResponse;
    }

    private String buildQueryString(Map<String, String> parameters) {
        if (parameters == null || parameters.isEmpty()) return "";
        return "?" + parameters.entrySet().stream()
                .map(entry -> {
                    try {
                        return entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString());
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.joining("&"));
    }
}
