package com.monnify.services.reservedaccount;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monnify.exceptions.MonnifyException;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.SearchResponse;
import com.monnify.models.transaction.IncomeSplitConfig;
import com.monnify.models.transaction.TransactionRecord;
import com.monnify.services.limitprofile.LimitProfileService;
import com.monnify.services.invoice.InvoiceService;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.account.*;
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
 * The {@link ReservedAccountService} class provides methods to interact with the Monnify API
 * for creating, updating, and managing reserved accounts.
 * @author Oreoluwa Somuyiwa
 */
public class ReservedAccountService {
    private static final Gson gson = new Gson();
    private static final MonnifyClient monnifyClient = new MonnifyClient();

    /**
     * Creates a reserved account using the provided request details.
     *
     * @param request The {@link ReservedAccountRequest} object containing the details required to create a reserved account.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the reserved account details in the {@link ReservedAccountResponse} object.
     * @throws MonnifyValidationException If no preferred banks are provided and {@link ReservedAccountRequest#isGetAllAvailableBanks()} is false.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<ReservedAccountResponse> createReservedAccount(ReservedAccountRequest request) {
        ValidationUtil.validate(request);
        if(!request.isGetAllAvailableBanks() && request.getPreferredBanks().isEmpty()){
            throw new MonnifyValidationException("Please provide at least one preferred bank");
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<ReservedAccountResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<ReservedAccountResponse>>() {};

        return monnifyClient.post(
                "/api/v2/bank-transfer/reserved-accounts",
                gson.toJson(request),
                headers,
                null,
                typeToken);
    }

    /**
     * Creates an invoice reserved account using the provided request details.
     * @see InvoiceService
     *
     * @param request The {@link ReservedAccountRequest} object containing the details required to create the invoice reserved account.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the reserved account details in the {@link ReservedAccountResponse} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<ReservedAccountResponse> createReservedAccountInvoice(ReservedAccountRequest request) {
        ValidationUtil.validate(request);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<ReservedAccountResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<ReservedAccountResponse>>() {};

        return monnifyClient.post(
                "/api/v1/bank-transfer/reserved-accounts",
                gson.toJson(request),
                headers,
                null,
                typeToken);
    }

    /**
     * Creates a reserved account with a transaction limit using the provided request details.
     * @see LimitProfileService
     *
     * @param request The {@link ReservedAccountRequest} object containing the details required to create a reserved account.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the reserved account details in the {@link ReservedAccountResponse} object.
     * @throws MonnifyValidationException If no preferred banks are provided and {@link ReservedAccountRequest#isGetAllAvailableBanks()} is false.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<ReservedAccountResponse> createReservedAccountWithLimit(ReservedAccountRequest request) {
        ValidationUtil.validate(request);
        if(!request.isGetAllAvailableBanks() && request.getPreferredBanks().isEmpty()){
            throw new MonnifyValidationException("Please provide at least one preferred bank");
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<ReservedAccountResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<ReservedAccountResponse>>() {};

        return monnifyClient.post(
                "/api/v2/bank-transfer/reserved-accounts/limit",
                gson.toJson(request),
                headers,
                null,
                typeToken);
    }

    /**
     * Updates the transaction limit profile of a reserved account using the provided request details.
     * @see LimitProfileService
     *
     * @param request The {@link UpdateReservedAccountLimitRequest} object containing the details required to update the limit.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the updated reserved account details in the {@link ReservedAccountResponse} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<ReservedAccountResponse> updateReservedAccountLimit(UpdateReservedAccountLimitRequest request) {
        ValidationUtil.validate(request);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<ReservedAccountResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<ReservedAccountResponse>>() {};

        return monnifyClient.put(
                "/api/v2/bank-transfer/reserved-accounts/limit",
                gson.toJson(request),
                headers,
                null,
                typeToken);
    }

    /**
     * Retrieves the details of a specific reserved account using its reference.
     *
     * @param accountReference The unique reference of the reserved account to retrieve.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the reserved account details in the {@link ReservedAccountResponse} object.
     * @throws MonnifyValidationException If accountReference is null or empty
     * @throws MonnifyException If an encoding error occurs while processing the request.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<ReservedAccountResponse> getReservedAccountDetails(String accountReference) {
        if(StringUtils.isNullOrEmpty(accountReference)) throw new MonnifyValidationException("Please provide an account reference");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<ReservedAccountResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<ReservedAccountResponse>>() {};

        try {
            return monnifyClient.get(
                    "/api/v2/bank-transfer/reserved-accounts/"+ URLEncoder.encode(accountReference, StandardCharsets.UTF_8.toString()),
                    headers,
                    null,
                    typeToken);
        } catch (UnsupportedEncodingException e) {
            throw new MonnifyException(e);
        }
    }

    /**
     * Adds linked virtual accounts to a reserved account using the provided request details.
     *
     * @param request The {@link AddLinkedAccountsRequest} object containing the details of the bankCodes of the linked accounts to add.
     * @param accountReference The unique reference of the reserved account to update.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the updated reserved account details in the {@link ReservedAccountResponse} object.
     * @throws MonnifyValidationException If accountReference is null or empty
     * @throws MonnifyValidationException If no preferred banks are provided and {@link ReservedAccountRequest#isGetAllAvailableBanks()}  is false.
     * @throws MonnifyException If an encoding error occurs while processing the request.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<ReservedAccountResponse> addLinkedAccounts(AddLinkedAccountsRequest request, String accountReference) {
        ValidationUtil.validate(request);
        if(StringUtils.isNullOrEmpty(accountReference)) throw new MonnifyValidationException("Please provide an account reference");
        if(!request.isGetAllAvailableBanks() && request.getPreferredBanks().isEmpty()){
            throw new MonnifyValidationException("Please provide at least one preferred bank");
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<ReservedAccountResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<ReservedAccountResponse>>() {};

        try {
            return monnifyClient.put(
                    "/api/v1/bank-transfer/reserved-accounts/add-linked-accounts/"+ URLEncoder.encode(accountReference, StandardCharsets.UTF_8.toString()),
                    gson.toJson(request),
                    headers,
                    null,
                    typeToken);
        } catch (UnsupportedEncodingException e) {
            throw new MonnifyException(e);
        }
    }

    /**
     * Updates the BVN (Bank Verification Number) associated with a reserved account.
     *
     * @param request The {@link UpdateBVNRequest} object containing the new BVN details.
     * @param accountReference The unique reference of the reserved account to update.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the updated reserved account details in the {@link ReservedAccountResponse} object.
     * @throws MonnifyValidationException If accountReference is null or empty
     * @throws MonnifyException If an encoding error occurs while processing the request.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<ReservedAccountResponse> updateBVN(UpdateBVNRequest request, String accountReference) {
        ValidationUtil.validate(request);
        if(StringUtils.isNullOrEmpty(accountReference)) throw new MonnifyValidationException("Please provide an account reference");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<ReservedAccountResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<ReservedAccountResponse>>() {};

        try {
            return monnifyClient.put(
                    "/api/v1/bank-transfer/reserved-accounts/update-customer-bvn/"+ URLEncoder.encode(accountReference, StandardCharsets.UTF_8.toString()),
                    gson.toJson(request),
                    headers,
                    null,
                    typeToken);
        } catch (UnsupportedEncodingException e) {
            throw new MonnifyException(e);
        }
    }

    /**
     * Deallocates a reserved account using its reference.
     *
     * @param accountReference The unique reference of the reserved account to deallocate.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the deallocated reserved account details in the {@link ReservedAccountResponse} object.
     * @throws MonnifyValidationException If accountReference is null or empty
     * @throws MonnifyException If an encoding error occurs while processing the request.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<ReservedAccountResponse> deallocateReservedAccount(String accountReference) {
        if(StringUtils.isNullOrEmpty(accountReference)) throw new MonnifyValidationException("Please provide an account reference");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<ReservedAccountResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<ReservedAccountResponse>>() {};

        try {
            return monnifyClient.delete(
                    "/api/v1/bank-transfer/reserved-accounts/reference/"+ URLEncoder.encode(accountReference, StandardCharsets.UTF_8.toString()),
                    "",
                    headers,
                    null,
                    typeToken);
        } catch (UnsupportedEncodingException e) {
            throw new MonnifyException(e);
        }
    }

    /**
     * Updates the allowed payment sources for a reserved account.
     *
     * @param request The {@link AllowedPaymentSourcesRequest} object containing the new payment source details.
     * @param accountReference The unique reference of the reserved account to update.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the updated payment source details in the {@link AllowedPaymentSourcesResponse} object.
     * @throws MonnifyValidationException If accountReference is null or empty
     * @throws MonnifyException If an encoding error occurs while processing the request.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<AllowedPaymentSourcesResponse> updateAllowedPaymentSources(AllowedPaymentSourcesRequest request, String accountReference) {
        ValidationUtil.validate(request);
        if(StringUtils.isNullOrEmpty(accountReference)) throw new MonnifyValidationException("Please provide an account reference");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<AllowedPaymentSourcesResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<AllowedPaymentSourcesResponse>>() {};

        try {
            return monnifyClient.put(
                    "/api/v1/bank-transfer/reserved-accounts/update-payment-source-filter/" + URLEncoder.encode(accountReference, StandardCharsets.UTF_8.toString()),
                    gson.toJson(request),
                    headers,
                    null,
                    typeToken);
        } catch (UnsupportedEncodingException e) {
            throw new MonnifyException(e);
        }
    }

    /**
     * Updates the income split configuration for a reserved account.
     *
     * @param request The list of {@link IncomeSplitConfig} objects containing the new split configuration details.
     * @param accountReference The unique reference of the reserved account to update.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the updated split configuration details in the {@link SplitConfigResponse} object.
     * @throws MonnifyValidationException If accountReference is null or empty.
     * @throws MonnifyException If an encoding error occurs while processing the request.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<SplitConfigResponse> updateSplitConfig(List<IncomeSplitConfig> request, String accountReference) {
        ValidationUtil.validate(request);
        if (StringUtils.isNullOrEmpty(accountReference)) throw new MonnifyValidationException("Please provide an account reference");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<SplitConfigResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<SplitConfigResponse>>() {};

        try {
            return monnifyClient.put(
                    "/api/v1/bank-transfer/reserved-accounts/update-income-split-config/" + URLEncoder.encode(accountReference, StandardCharsets.UTF_8.toString()),
                    gson.toJson(request),
                    headers,
                    null,
                    typeToken);
        } catch (UnsupportedEncodingException e) {
            throw new MonnifyException(e);
        }
    }

    /**
     * Retrieves a paginated list of transactions for a reserved account.
     *
     * @param accountReference The unique reference of the reserved account.
     * @param page The page number (zero-based) for pagination.
     * @param size The number of records per page.
     * @return A {@link MonnifyBaseResponse} with a {@link SearchResponse} object containing a list of {@link TransactionRecord} objects.
     * @throws MonnifyValidationException If accountReference is null or empty.
     * @throws MonnifyException If an encoding error occurs while processing the request.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<SearchResponse<TransactionRecord>> getReservedAccountTransactions(String accountReference, Integer page, Integer size) {
        if (StringUtils.isNullOrEmpty(accountReference)) throw new MonnifyValidationException("Please provide an account reference");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        Map<String, String> parameters = new HashMap<>();
        if(page != null) {
            parameters.put("page", page.toString());
        }
        if(size != null) {
            parameters.put("size", size.toString());
        }
        parameters.put("accountReference", accountReference);

        TypeToken<MonnifyBaseResponse<SearchResponse<TransactionRecord>>> typeToken =
                new TypeToken<MonnifyBaseResponse<SearchResponse<TransactionRecord>>>() {};

        return monnifyClient.get(
                "/api/v1/bank-transfer/reserved-accounts/transactions",
                headers,
                parameters,
                typeToken);
    }

    /**
     * Updates the KYC information (BVN/NIN) linked to a reserved account.
     *
     * @param request The {@link KycInfoRequest} object containing the BVN and/or NIN.
     * @param accountReference The unique reference of the reserved account.
     * @return A {@link MonnifyBaseResponse} containing the updated KYC details in {@link KycInfoResponse}.
     * @throws MonnifyValidationException If accountReference is null or empty.
     * @throws MonnifyValidationException If no BVN or NIN is supplied
     * @throws MonnifyException If an encoding error occurs while processing the request.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<KycInfoResponse> updateKycInfo(KycInfoRequest request, String accountReference) {
        if (StringUtils.isNullOrEmpty(accountReference)) {
            throw new MonnifyValidationException("Please provide an account reference");
        }

        if(StringUtils.isNullOrEmpty(request.getNin()) && StringUtils.isNullOrEmpty(request.getBvn())){
            throw new MonnifyValidationException("Please provide an nin or bvn to make this request");
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<KycInfoResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<KycInfoResponse>>() {};

        try {
            return monnifyClient.put(
                    "/api/v1/bank-transfer/reserved-accounts/" + URLEncoder.encode(accountReference, StandardCharsets.UTF_8.toString()) + "/kyc-info",
                    gson.toJson(request),
                    headers,
                    null,
                    typeToken);
        } catch (UnsupportedEncodingException e) {
            throw new MonnifyException(e);
        }
    }

}
