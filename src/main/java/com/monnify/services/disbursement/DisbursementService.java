package com.monnify.services.disbursement;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monnify.exceptions.MonnifyException;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.disbursement.*;
import com.monnify.models.wallet.WalletBalanceResponse;
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
 * Service for managing Monnify disbursements, including single and batch transactions.
 * Provides methods for initiating disbursements, validating OTPs, checking summaries,
 * and searching for disbursement transactions.
 * @author Oreoluwa Somuyiwa
 */
public class DisbursementService {
    private static final Gson gson = new Gson();
    private static final MonnifyClient monnifyClient = new MonnifyClient();

    /**
     * Initiates a single disbursement transaction.
     *
     * @param request The request payload containing disbursement details.
     * @return A {@link MonnifyBaseResponse} containing a {@link SingleDisbursementResponse}.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<SingleDisbursementResponse> disburseSingle(SingleDisbursementRequest request) {
        ValidationUtil.validate(request);


        TypeToken<MonnifyBaseResponse<SingleDisbursementResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<SingleDisbursementResponse>>() {};

        return monnifyClient.post(
                "/api/v2/disbursements/single",
                gson.toJson(request),
                null,
                null,
                typeToken);
    }

    /**
     * Initiates a batch disbursement transaction.
     *
     * @param request The request payload containing batch disbursement details.
     * @return A {@link MonnifyBaseResponse} containing a {@link BatchDisbursementResponse}.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<BatchDisbursementResponse> disburseBatch(BatchDisbursementRequest request) {
        ValidationUtil.validate(request);


        TypeToken<MonnifyBaseResponse<BatchDisbursementResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<BatchDisbursementResponse>>() {};

        return monnifyClient.post(
                "/api/v2/disbursements/batch",
                gson.toJson(request),
                null,
                null,
                typeToken);
    }

    /**
     * Validates an OTP for a single disbursement transaction.
     *
     * @param request The request payload containing OTP details.
     * @return A {@link MonnifyBaseResponse} containing a {@link SingleDisbursementResponse}.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<SingleDisbursementResponse> validateDisbursementOtp(ValidateOtpRequest request) {
        ValidationUtil.validate(request);


        TypeToken<MonnifyBaseResponse<SingleDisbursementResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<SingleDisbursementResponse>>() {};

        return monnifyClient.post(
                "/api/v2/disbursements/single/validate-otp",
                gson.toJson(request),
                null,
                null,
                typeToken);
    }

    /**
     * Validates an OTP for a batch disbursement transaction.
     *
     * @param request The request payload containing OTP details.
     * @return A {@link MonnifyBaseResponse} containing a {@link BatchDisbursementResponse}.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<BatchDisbursementResponse> validateBatchDisbursementOtp(ValidateOtpRequest request) {
        ValidationUtil.validate(request);


        TypeToken<MonnifyBaseResponse<BatchDisbursementResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<BatchDisbursementResponse>>() {};

        return monnifyClient.post(
                "/api/v2/disbursements/batch/validate-otp",
                gson.toJson(request),
                null,
                null,
                typeToken);
    }

    /**
     * Resends an OTP for a single disbursement transaction.
     *
     * @param request The request payload containing resend OTP details.
     * @return A {@link MonnifyBaseResponse} containing a {@link ResendOtpResponse}.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<ResendOtpResponse> resendDisbursementOtp(ResendOtpRequest request) {
        ValidationUtil.validate(request);


        TypeToken<MonnifyBaseResponse<ResendOtpResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<ResendOtpResponse>>() {};

        return monnifyClient.post(
                "/api/v2/disbursements/single/resend-otp",
                gson.toJson(request),
                null,
                null,
                typeToken);
    }

    /**
     * Retrieves the summary of a single disbursement transaction.
     *
     * @param reference The unique reference of the disbursement.
     * @return A {@link MonnifyBaseResponse} containing a {@link DisbursementSummaryResponse}.
     * @author Oreoluwa Somuyiwa
     * @throws MonnifyValidationException If reference is null or empty
     */
    public MonnifyBaseResponse<DisbursementSummaryResponse> getDisbursementSummary(String reference) {

        if(StringUtils.isNullOrEmpty(reference)) throw new MonnifyValidationException("reference is null or empty");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("reference", reference);

        TypeToken<MonnifyBaseResponse<DisbursementSummaryResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<DisbursementSummaryResponse>>() {};

        return monnifyClient.get(
                "/api/v2/disbursements/single/summary",
                null,
                parameters,
                typeToken);
    }

    /**
     * Retrieves a paginated list of all single disbursements.
     *
     * @param pageSize The number of records per page.
     * @param pageNo The page number to retrieve, 0-index.
     * @return A {@link MonnifyBaseResponse} containing {@link SearchResponse} of {@link DisbursementSummaryResponse}
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<SearchResponse<DisbursementSummaryResponse>> getAllSingleDisbursements(Integer pageSize, Integer pageNo){

        Map<String, String> parameters = new HashMap<>();
        if(pageNo != null) {
            parameters.put("pageNo", pageNo.toString());
        }
        if(pageSize != null) {
            parameters.put("pageSize", pageSize.toString());
        }

        TypeToken<MonnifyBaseResponse<SearchResponse<DisbursementSummaryResponse>>> typeToken =
                new TypeToken<MonnifyBaseResponse<SearchResponse<DisbursementSummaryResponse>>>() {};

        return monnifyClient.get(
                "/api/v2/disbursements/single/transactions",
                null,
                parameters,
                typeToken);
    }

    /**
     * Retrieves a paginated list of all batch disbursements.
     *
     * @param pageSize The number of records per page.
     * @param pageNo The page number to retrieve.
     * @return A {@link MonnifyBaseResponse} containing {@link SearchResponse} of {@link BatchDisbursementResponse}.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<SearchResponse<BatchDisbursementResponse>> getAllBulkDisbursements(Integer pageSize, Integer pageNo){

        Map<String, String> parameters = new HashMap<>();
        if(pageNo != null) {
            parameters.put("pageNo", pageNo.toString());
        }
        if(pageSize != null) {
            parameters.put("pageSize", pageSize.toString());
        }

        TypeToken<MonnifyBaseResponse<SearchResponse<BatchDisbursementResponse>>> typeToken =
                new TypeToken<MonnifyBaseResponse<SearchResponse<BatchDisbursementResponse>>>() {};

        return monnifyClient.get(
                "/api/v2/disbursements/bulk/transactions",
                null,
                parameters,
                typeToken);
    }

    /**
     * Retrieves a paginated list of bulk transfer transactions for a given batch reference.
     *
     * @param batchReference The unique batch reference.
     * @param pageSize The number of records per page.
     * @param pageNo The page number to retrieve.
     * @return A {@code MonnifyBaseResponse} containing {@link SearchResponse} of {@link DisbursementSummaryResponse} in single bulk transfer.
     * @author Oreoluwa Somuyiwa
     * @throws MonnifyValidationException If batchReference is null or empty
     * @throws MonnifyException If encoding exception occurs
     */
    public MonnifyBaseResponse<SearchResponse<DisbursementSummaryResponse>> getBulkTransferTransactions(String batchReference, Integer pageSize, Integer pageNo){

        if(StringUtils.isNullOrEmpty(batchReference)) throw new MonnifyValidationException("batchReference is null or empty");

        Map<String, String> parameters = new HashMap<>();
        if(pageNo != null) {
            parameters.put("pageNo", pageNo.toString());
        }
        if(pageSize != null) {
            parameters.put("pageSize", pageSize.toString());
        }

        TypeToken<MonnifyBaseResponse<SearchResponse<DisbursementSummaryResponse>>> typeToken =
                new TypeToken<MonnifyBaseResponse<SearchResponse<DisbursementSummaryResponse>>>() {};

        try {
            return monnifyClient.get(
                    "/api/v2/disbursements/bulk/" + URLEncoder.encode(batchReference, StandardCharsets.UTF_8.toString()) + "/transactions",
                    null,
                    parameters,
                    typeToken);
        } catch (UnsupportedEncodingException e) {
            throw new MonnifyException(e);
        }
    }

    /**
     * Retrieves the summary of a bulk disbursement transaction.
     *
     * @param batchReference The unique reference of the batch disbursement.
     * @return A {@link MonnifyBaseResponse} containing a {@link BulkDisbursementSummary}.
     * @author Oreoluwa Somuyiwa
     * @throws MonnifyValidationException If batchReference is null or empty
     */
    public MonnifyBaseResponse<BulkDisbursementSummary> getBulkTransferStatus(String batchReference){
        if(StringUtils.isNullOrEmpty(batchReference)) throw new MonnifyValidationException("batchReference is null or empty");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("reference", batchReference);

        TypeToken<MonnifyBaseResponse<BulkDisbursementSummary>> typeToken =
                new TypeToken<MonnifyBaseResponse<BulkDisbursementSummary>>() {};

        return monnifyClient.get(
                "/api/v2/disbursements/batch/summary",
                null,
                parameters,
                typeToken);
    }

    /**
     * Searches for disbursement transactions based on filters such as source account number,
     * date range, and amount range.
     *
     * @param request The request payload containing search parameters.
     * @return A {@link MonnifyBaseResponse} containing {@link SearchResponse} of {@link TransferDetails}.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<SearchResponse<TransferDetails>> searchTransactions(SearchTransactionsRequest request) {
        Map<String, String> queryParams = new HashMap<>();
        if (!StringUtils.isNullOrEmpty(request.getSourceAccountNumber())) {
            queryParams.put("sourceAccountNumber", request.getSourceAccountNumber());
        }

        if (request.getPageSize() != null) {
            queryParams.put("pageSize", String.valueOf(request.getPageSize()));
        }

        if (request.getPageNo() != null) {
            queryParams.put("pageNo", String.valueOf(request.getPageNo()));
        }

        if (!StringUtils.isNullOrEmpty(request.getStartDate())) {
            queryParams.put("startDate", request.getStartDate());
        }

        if (!StringUtils.isNullOrEmpty(request.getEndDate())) {
            queryParams.put("endDate", request.getEndDate());
        }

        if (request.getAmountFrom() != null) {
            queryParams.put("amountFrom", request.getAmountFrom());
        }

        if (request.getAmountTo() != null) {
            queryParams.put("amountTo", request.getAmountTo());
        }

        TypeToken<MonnifyBaseResponse<SearchResponse<TransferDetails>>> typeToken =
                new TypeToken<MonnifyBaseResponse<SearchResponse<TransferDetails>>>() {};

        return monnifyClient.get(
                "/api/v2/disbursements/search-transactions",
                null,
                queryParams,
                typeToken
        );
    }

    /**
     * Retrieves the balance of a Merchant's wallet using their wallet account number.
     *
     * @param walletAccountNumber The account number of the merchant's wallet.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the wallet balance details in the {@link WalletBalanceResponse} object.
     * @throws MonnifyValidationException If walletAccountNumber is null or empty
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<WalletBalanceResponse> getWalletBalance(String walletAccountNumber){
        if(StringUtils.isNullOrEmpty(walletAccountNumber)) throw new MonnifyValidationException("walletAccountNumber is null or empty");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("accountNumber", walletAccountNumber);

        TypeToken<MonnifyBaseResponse<WalletBalanceResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<WalletBalanceResponse>>() {};

        return monnifyClient.get(
                "/api/v2/disbursements/wallet-balance",
                null,
                parameters,
                typeToken);
    }
}
