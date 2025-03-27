package com.monnify.services.transaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monnify.exceptions.MonnifyException;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.transaction.ChargeCardTokenRequest;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.transaction.*;
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
 * The {@link TransactionService} class provides methods to interact with the Monnify API
 * for initializing transactions, charging cards, and retrieving transaction statuses.
 * @author Oreoluwa Somuyiwa
 */
public class TransactionService {
    private static final Gson gson = new Gson();
    private static final MonnifyClient monnifyClient = new MonnifyClient();

    /**
     * Initializes a transaction using the provided transaction request details.
     *
     * @param transactionRequest The {@link TransactionRequest} object containing the details required to initialize a transaction.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the transaction details in the {@link TransactionResponse} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<TransactionResponse> initializeTransaction(TransactionRequest transactionRequest){
        ValidationUtil.validate(transactionRequest);
        TypeToken<MonnifyBaseResponse<TransactionResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<TransactionResponse>>() {};

        return monnifyClient.post(
                "/api/v1/merchant/transactions/init-transaction",
                gson.toJson(transactionRequest),
                null,
                null,
                typeToken
        );
    }

    /**
     * Initiates a payment using bank transfer with the provided request details.
     *
     * @param bankTransferRequest The {@link BankTransferRequest} object containing the details required to initiate a bank transfer payment.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the account details for the bank transfer in the {@link BankTransferResponse} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<BankTransferResponse> payWithBankTransfer(BankTransferRequest bankTransferRequest){
        ValidationUtil.validate(bankTransferRequest);
        TypeToken<MonnifyBaseResponse<BankTransferResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<BankTransferResponse>>() {};

        return monnifyClient.post(
                "/api/v1/merchant/bank-transfer/init-payment",
                gson.toJson(bankTransferRequest),
                null,
                null,
                typeToken);
    }

    /**
     * Charges a card using the provided charge card request details.
     *
     * @param chargeCardRequest The {@link ChargeCardRequest} object containing the details required to charge a card.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the card charge details in the {@link ChargeCardResponse} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<ChargeCardResponse> chargeCard(ChargeCardRequest chargeCardRequest) {
        ValidationUtil.validate(chargeCardRequest);

        TypeToken<MonnifyBaseResponse<ChargeCardResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<ChargeCardResponse>>() {};

        return monnifyClient.post(
                "/api/v1/merchant/cards/charge",
                gson.toJson(chargeCardRequest),
                null,
                null,
                typeToken
        );
    }

    /**
     * Charges a card using a saved card token with the provided request details.
     *
     * @param chargeCardTokenRequest The {@link ChargeCardTokenRequest} object containing the details required to charge a card token.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the transaction status in the {@link TransactionStatusResponse} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<TransactionStatusResponse> chargeCardToken(ChargeCardTokenRequest chargeCardTokenRequest) {
        ValidationUtil.validate(chargeCardTokenRequest);

        TypeToken<MonnifyBaseResponse<TransactionStatusResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<TransactionStatusResponse>>() {};

        return monnifyClient.post(
                "/api/v1/merchant/cards/charge-card-token",
                gson.toJson(chargeCardTokenRequest),
                null,
                null,
                typeToken
        );
    }

    /**
     * Authorizes a card charge using an OTP (One-Time Password) with the provided request details.
     *
     * @param authorizeOtpRequest The {@link AuthorizeOtpRequest} object containing the details required to authorize the OTP.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the card charge details in the {@link ChargeCardResponse} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<ChargeCardResponse> authorizeCardOtp(AuthorizeOtpRequest authorizeOtpRequest) {
        ValidationUtil.validate(authorizeOtpRequest);

        TypeToken<MonnifyBaseResponse<ChargeCardResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<ChargeCardResponse>>() {};

        return monnifyClient.post(
                "/api/v1/merchant/cards/otp/authorize",
                gson.toJson(authorizeOtpRequest),
                null,
                null,
                typeToken
        );
    }

    /**
     * Searches for transactions based on the specified criteria.
     *
     * @param page The page number for pagination (optional).
     * @param size The number of items per page (optional).
     * @param paymentReference The payment reference to filter by (optional).
     * @param transactionReference The transaction reference to filter by (optional).
     * @param fromAmount The minimum amount to filter by (optional).
     * @param toAmount The maximum amount to filter by (optional).
     * @param amount The exact amount to filter by (optional).
     * @param customerName The customer name to filter by (optional).
     * @param customerEmail The customer email to filter by (optional).
     * @param paymentStatusParam The payment status to filter by (optional).
     * @param fromTimestamp The start timestamp in milliseconds for the search range (optional).
     * @param toTimestamp The end timestamp in milliseconds for the search range (optional).
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including a list of transactions in the {@link SearchResponse<TransactionRecord>} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<SearchResponse<TransactionRecord>> searchTransactions(
            Integer page, Integer size, String paymentReference, String transactionReference,
            Float fromAmount, Float toAmount, Float amount, String customerName,
            String customerEmail, String paymentStatusParam, Long fromTimestamp, Long toTimestamp
    ) {

        Map<String, String> parameters = new HashMap<>();
        if(page != null) {
            parameters.put("pageNo", page.toString());
        }
        if(size != null) {
            parameters.put("pageSize", size.toString());
        }
        if (!StringUtils.isNullOrEmpty(paymentReference)) parameters.put("paymentReference", paymentReference);
        if (!StringUtils.isNullOrEmpty(transactionReference)) parameters.put("transactionReference", transactionReference);
        if (fromAmount != null) parameters.put("fromAmount", String.valueOf(fromAmount));
        if (toAmount != null) parameters.put("toAmount", String.valueOf(toAmount));
        if (amount != null) parameters.put("amount", String.valueOf(amount));
        if (!StringUtils.isNullOrEmpty(customerName)) parameters.put("customerName", customerName);
        if (!StringUtils.isNullOrEmpty(customerEmail)) parameters.put("customerEmail", customerEmail);
        if (!StringUtils.isNullOrEmpty(paymentStatusParam)) parameters.put("paymentStatusParam", paymentStatusParam);
        if (fromTimestamp != null) parameters.put("from", String.valueOf(fromTimestamp));
        if (toTimestamp != null) parameters.put("to", String.valueOf(toTimestamp));

        TypeToken<MonnifyBaseResponse<SearchResponse<TransactionRecord>>> typeToken =
                new TypeToken<MonnifyBaseResponse<SearchResponse<TransactionRecord>>>() {};

        return monnifyClient.get(
                "/api/v1/transactions/search",
                null,
                parameters,
                typeToken
        );
    }

    /**
     * Retrieves the status of a specific transaction using its transaction reference.
     *
     * @param transactionReference The unique reference of the transaction to retrieve.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the transaction status in the {@link TransactionStatusResponse} object.
     * @throws MonnifyValidationException If the transaction reference is null or empty.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<TransactionStatusResponse> getStatus(String transactionReference) {
        if(StringUtils.isNullOrEmpty(transactionReference)) {
            throw new MonnifyValidationException("TransactionReference is null or empty.");
        }

        String endpoint = String.format("/api/v2/transactions/%s", URLEncoder.encode(transactionReference));

        TypeToken<MonnifyBaseResponse<TransactionStatusResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<TransactionStatusResponse>>() {};

        return monnifyClient.get(endpoint, null, null, typeToken);
    }

    /**
     * Retrieves the status of a transaction using either a payment reference or a transaction reference.
     *
     * @param paymentReference The payment reference to retrieve the transaction status (optional).
     * @param transactionReference The transaction reference to retrieve the transaction status (optional).
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the transaction status in the {@link TransactionStatusResponse} object.
     * @throws MonnifyValidationException If neither a payment reference nor a transaction reference is provided,
     *         or if both are provided simultaneously.
     * @throws MonnifyException If an encoding error occurs while processing the request.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<TransactionStatusResponse> getStatusByReference(String paymentReference, String transactionReference) {
        if(StringUtils.isNullOrEmpty(paymentReference) && StringUtils.isNullOrEmpty(transactionReference)) {
            throw new MonnifyValidationException("Kindly supply either a payment or transaction reference.");
        } else if (!StringUtils.isNullOrEmpty(paymentReference) && !StringUtils.isNullOrEmpty(transactionReference)) {
            throw new MonnifyValidationException("Please use either payment or transaction reference, to avoid unexpected behaviour");
        }


        Map<String, String> parameters = new HashMap<>();

        if(!StringUtils.isNullOrEmpty(paymentReference)) {
            parameters.put("paymentReference", paymentReference);
        }

        if(!StringUtils.isNullOrEmpty(transactionReference)) {
            parameters.put("transactionReference", transactionReference);
        }

        TypeToken<MonnifyBaseResponse<TransactionStatusResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<TransactionStatusResponse>>() {};

        return monnifyClient.get(
                "/api/v2/merchant/transactions/query",
                null,
                parameters,
                typeToken
        );
    }
}
