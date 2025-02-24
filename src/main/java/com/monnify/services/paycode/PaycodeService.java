package com.monnify.services.paycode;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monnify.exceptions.MonnifyException;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.paycode.PaycodeRequest;
import com.monnify.models.paycode.PaycodeResponse;
import com.monnify.utils.MonnifyClient;
import com.monnify.utils.StringUtils;
import com.monnify.utils.ValidationUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.monnify.services.auth.AuthService.getToken;

/**
 * The {@link PaycodeService} class provides methods to interact with the Monnify API
 * for creating, retrieving, and managing paycodes.
 */
public class PaycodeService {
    private static final Gson gson = new Gson();
    private static final MonnifyClient monnifyClient = new MonnifyClient();

    /**
     * Creates a new paycode using the provided request details.
     *
     * @param request The {@link PaycodeRequest} object containing the details required to create a paycode.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the created paycode details in the {@link PaycodeResponse} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<PaycodeResponse> createPaycode(PaycodeRequest request) {
        ValidationUtil.validate(request);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<PaycodeResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<PaycodeResponse>>() {};

        return monnifyClient.post(
                "/api/v1/paycode",
                gson.toJson(request),
                headers,
                null,
                typeToken);
    }

    /**
     * Retrieves the details of a specific paycode using its reference.
     *
     * @param paycodeReference The unique reference of the paycode to retrieve.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the paycode details in the {@link PaycodeResponse} object.
     * @throws MonnifyValidationException If paycodeReference is null or empty
     * @throws MonnifyException If an encoding error occurs while processing the request.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<PaycodeResponse> getPaycode(String paycodeReference) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        if(StringUtils.isNullOrEmpty(paycodeReference)) throw new MonnifyValidationException("paycodeReference is empty");
        TypeToken<MonnifyBaseResponse<PaycodeResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<PaycodeResponse>>() {};

        try {
            return monnifyClient.get(
                    "/api/v1/paycode/" + URLEncoder.encode(paycodeReference, StandardCharsets.UTF_8.toString()),
                    headers,
                    null,
                    typeToken);
        } catch (UnsupportedEncodingException e) {
            throw new MonnifyException(e);
        }
    }

    /**
     * Retrieves the details of a specific paycode after clearing it for authorization.
     *
     * @param paycodeReference The unique reference of the paycode to retrieve and clear.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the paycode details in the {@link PaycodeResponse} object.
     * @throws MonnifyValidationException If paycodeReference is null or empty
     * @throws MonnifyException If an encoding error occurs while processing the request.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<PaycodeResponse> getClearPaycode(String paycodeReference) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        if(StringUtils.isNullOrEmpty(paycodeReference)) throw new MonnifyValidationException("paycodeReference is empty");
        TypeToken<MonnifyBaseResponse<PaycodeResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<PaycodeResponse>>() {};

        try {
            return monnifyClient.get(
                    "/api/v1/paycode/" + URLEncoder.encode(paycodeReference, StandardCharsets.UTF_8.toString()) + "/authorize",
                    headers,
                    null,
                    typeToken);
        } catch (UnsupportedEncodingException e) {
            throw new MonnifyException(e);
        }
    }

    /**
     * Fetches a list of paycodes based on the specified search criteria.
     *
     * @param transactionReference The transaction reference associated with the paycodes.
     * @param beneficiaryName The name of the beneficiary associated with the paycodes.
     * @param transactionStatus The status of the transactions associated with the paycodes.
     * @param from The start timestamp for the search range.
     * @param to The end timestamp for the search range.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including a list of paycode details in the {@link SearchResponse} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<SearchResponse<PaycodeResponse>> fetchPaycodes(String transactionReference, String beneficiaryName, String transactionStatus, long from, long to) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        Map<String, String> parameters = new HashMap<>();
        parameters.put("transactionReference", transactionReference);
        parameters.put("beneficiaryName", beneficiaryName);
        parameters.put("transactionStatus", transactionStatus);
        parameters.put("from", String.valueOf(from));
        parameters.put("to", String.valueOf(to));

        TypeToken<MonnifyBaseResponse<SearchResponse<PaycodeResponse>>> typeToken =
                new TypeToken<MonnifyBaseResponse<SearchResponse<PaycodeResponse>>>() {};

        return monnifyClient.get(
                "/api/v1/paycode",
                headers,
                parameters,
                typeToken);
    }

    /**
     * Deletes a specific paycode using its reference.
     *
     * @param paycodeReference The unique reference of the paycode to delete.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the deleted paycode details in the {@link PaycodeResponse} object.
     * @throws MonnifyValidationException If paycodeReference is null or empty
     * @throws MonnifyException If an encoding error occurs while processing the request.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<PaycodeResponse> deletePaycode(String paycodeReference) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        if(StringUtils.isNullOrEmpty(paycodeReference)) throw new MonnifyValidationException("paycodeReference is empty");
        TypeToken<MonnifyBaseResponse<PaycodeResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<PaycodeResponse>>() {};

        try {
            return monnifyClient.delete(
                    "/api/v1/paycode/" + URLEncoder.encode(paycodeReference, StandardCharsets.UTF_8.toString()),
                    "",
                    headers,
                    null,
                    typeToken);
        } catch (UnsupportedEncodingException e) {
            throw new MonnifyException(e);
        }
    }
}
