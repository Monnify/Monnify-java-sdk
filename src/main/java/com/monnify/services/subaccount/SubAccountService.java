package com.monnify.services.subaccount;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monnify.exceptions.MonnifyException;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.subaccount.SubAccountRequest;
import com.monnify.models.subaccount.SubAccountResponse;
import com.monnify.models.subaccount.UpdateSubAccountRequest;
import com.monnify.utils.MonnifyClient;
import com.monnify.utils.StringUtils;
import com.monnify.utils.ValidationUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.monnify.services.auth.AuthService.getToken;

/**
 * The {@link SubAccountService} class provides methods to interact with the Monnify API
 * for creating, updating, deleting, and retrieving sub-accounts.
 * @author Oreoluwa Somuyiwa
 */
public class SubAccountService {
    private static final Gson gson = new Gson();
    private static final MonnifyClient monnifyClient = new MonnifyClient();

    /**
     * Creates multiple sub-accounts using the provided list of sub-account requests.
     *
     * @param subAccountRequests A list of {@link SubAccountRequest} objects containing the details required to create sub-accounts.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including a list of created sub-accounts in the {@link List<SubAccountResponse>} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<List<SubAccountResponse>> createSubAccounts(List<SubAccountRequest> subAccountRequests) {
        ValidationUtil.validate(subAccountRequests);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<List<SubAccountResponse>>> typeToken =
                new TypeToken<MonnifyBaseResponse<List<SubAccountResponse>>>() {};

        return monnifyClient.post(
                "/api/v1/sub-accounts",
                gson.toJson(subAccountRequests),
                headers,
                null,
                typeToken
        );
    }

    /**
     * Deletes a specific sub-account using its sub-account code.
     *
     * @param subAccountCode The unique code of the sub-account to delete.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         with no additional data (void).
     * @throws MonnifyValidationException If subAccountCode is null or empty
     * @throws MonnifyException If an encoding error occurs while processing the request.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<Void> deleteSubAccount(String subAccountCode) {
        if(StringUtils.isNullOrEmpty(subAccountCode)) throw new MonnifyValidationException("subAccountCode is empty");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<Void>> typeToken =
                new TypeToken<MonnifyBaseResponse<Void>>() {};

        try {
            return monnifyClient.delete(
                    "/api/v1/sub-accounts/" + URLEncoder.encode(subAccountCode, StandardCharsets.UTF_8.toString()),
                    "",
                    headers,
                    null,
                    typeToken
            );
        } catch (UnsupportedEncodingException e) {
            throw new MonnifyException(e);
        }
    }

    /**
     * Retrieves a list of all sub-accounts.
     *
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including a list of sub-accounts in the {@link List<SubAccountResponse>} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<List<SubAccountResponse>> getSubAccounts() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<List<SubAccountResponse>>> typeToken =
                new TypeToken<MonnifyBaseResponse<List<SubAccountResponse>>>() {};

        return monnifyClient.get(
                "/api/v1/sub-accounts",
                headers,
                null,
                typeToken
        );
    }

    /**
     * Updates a specific sub-account using the provided update request.
     *
     * @param updateSubAccountRequest The {@link UpdateSubAccountRequest} object containing the details required to update the sub-account.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the updated sub-account details in the {@link SubAccountResponse} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<SubAccountResponse> updateSubAccount(UpdateSubAccountRequest updateSubAccountRequest) {
        ValidationUtil.validate(updateSubAccountRequest);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<SubAccountResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<SubAccountResponse>>() {};

        return monnifyClient.put(
                "/api/v1/sub-accounts",
                gson.toJson(updateSubAccountRequest),
                headers,
                null,
                typeToken
        );
    }
}
