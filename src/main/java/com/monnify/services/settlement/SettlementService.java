package com.monnify.services.settlement;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monnify.exceptions.MonnifyException;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.settlement.SettlementResponse;
import com.monnify.models.transaction.TransactionStatusResponse;
import com.monnify.utils.MonnifyClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.monnify.services.auth.AuthService.getToken;

/**
 * The {@link SettlementService} class provides methods to interact with the Monnify API
 * for retrieving settlement and transaction information.
 * @author Oreoluwa Somuyiwa
 */
public class SettlementService {
    private static final Gson gson = new Gson();
    private static final MonnifyClient monnifyClient = new MonnifyClient();

    /**
     * Retrieves a list of transactions associated with a specific settlement reference.
     *
     * @param reference The settlement reference to search for transactions.
     * @param page The page number for pagination, 0-index.
     * @param size The number of items per page.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including a list of transactions in the {@link SearchResponse<TransactionStatusResponse>} object.
     * @throws MonnifyValidationException If the reference is null, empty, or if the size is less than or equal to 0.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<SearchResponse<TransactionStatusResponse>> getTransactionsBySettlementReference(String reference, int page, int size) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        if(reference == null || reference.isEmpty() || size <= 0) {
            throw new MonnifyValidationException("Reference is required, size must be greater than 0");
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("reference", reference);
        parameters.put("page", String.valueOf(page));
        parameters.put("size", String.valueOf(size));

        TypeToken<MonnifyBaseResponse<SearchResponse<TransactionStatusResponse>>> typeToken =
                new TypeToken<MonnifyBaseResponse<SearchResponse<TransactionStatusResponse>>>() {};

        return monnifyClient.get(
                "/api/v1/transactions/find-by-settlement-reference",
                headers,
                parameters,
                typeToken
        );
    }

    /**
     * Retrieves settlement information for a specific transaction using its reference.
     *
     * @param transactionReference The unique reference of the transaction to retrieve settlement details for.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the settlement details in the {@link SettlementResponse} object.
     * @throws MonnifyException If an encoding error occurs while processing the request.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<SettlementResponse> getSettlementInformation(String transactionReference) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<SettlementResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<SettlementResponse>>() {};

        try {
            return monnifyClient.get(
                    "/api/v1/settlement-detail?transactionReference=" + URLEncoder.encode(transactionReference, StandardCharsets.UTF_8.toString()),
                    headers,
                    null,
                    typeToken
            );
        } catch (UnsupportedEncodingException e) {
            throw new MonnifyException(e);
        }
    }
}
