package com.monnify.services.directdebit;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monnify.exceptions.MonnifyException;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.directdebit.*;
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
 * Service for managing direct debit mandates using the Monnify API.
 * Provides functionality to create mandates, retrieve mandate details,
 * initiate debits, check debit status, and cancel mandates.
 * @author Oreoluwa Somuyiwa
 */
public class DirectDebitService {
    private static final Gson gson = new Gson();
    private static final MonnifyClient monnifyClient = new MonnifyClient();

    /**
     * Creates a new direct debit mandate.
     *
     * @param mandateRequest The request payload containing mandate details of type {@link MandateRequest}.
     * @return A {@link MonnifyBaseResponse} containing a {@link MandateResponse}.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<MandateResponse> createMandate(MandateRequest mandateRequest){
        ValidationUtil.validate(mandateRequest);
        TypeToken<MonnifyBaseResponse<MandateResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<MandateResponse>>() {};

        return monnifyClient.post(
                "/api/v1/direct-debit/mandate/create",
                gson.toJson(mandateRequest),
                null,
                null,
                typeToken
        );
    }

    /**
     * Retrieves the details of a direct debit mandate using its reference.
     *
     * @param mandateReference The unique reference of the mandate.
     * @return A {@link MonnifyBaseResponse} containing a list of {@link MandateStatusResponse}.
     * @author Oreoluwa Somuyiwa
     * @throws MonnifyValidationException If mandateReference is null or empty
     * @throws MonnifyException If encoding exception occurs
     */
    public MonnifyBaseResponse<List<MandateStatusResponse>> getMandate(String mandateReference) {

        if(StringUtils.isNullOrEmpty(mandateReference)) throw new MonnifyValidationException("paymentReference is empty");

        TypeToken<MonnifyBaseResponse<List<MandateStatusResponse>>> typeToken =
                new TypeToken<MonnifyBaseResponse<List<MandateStatusResponse>>>() {};

        return monnifyClient.get(
                "/api/v1/direct-debit/mandate/?mandateReferences=" + mandateReference,
                null,
                null,
                typeToken
        );
    }

    /**
     * Initiates a debit transaction on an existing mandate.
     *
     * @param mandateDebitRequest The request payload containing debit details.
     * @return A {@link MonnifyBaseResponse} containing a {@link MandateDebitResponse}.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<MandateDebitResponse> debitMandate(MandateDebitRequest mandateDebitRequest) {
        ValidationUtil.validate(mandateDebitRequest);


        TypeToken<MonnifyBaseResponse<MandateDebitResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<MandateDebitResponse>>() {};

        return monnifyClient.post(
                "/api/v1/direct-debit/mandate/debit",
                gson.toJson(mandateDebitRequest),
                null,
                null,
                typeToken
        );
    }

    /**
     * Retrieves the status of a direct debit transaction using its payment reference.
     *
     * @param paymentReference The unique payment reference for the debit transaction.
     * @return A {@link MonnifyBaseResponse} containing a {@link MandateDebitResponse}.
     * @author Oreoluwa Somuyiwa
     * @throws MonnifyValidationException If paymentReference is null or empty
     * @throws MonnifyException If encoding exception occurs
     */
    public MonnifyBaseResponse<MandateDebitResponse> getDebitStatus(String paymentReference) {

        if(StringUtils.isNullOrEmpty(paymentReference)) throw new MonnifyValidationException("paymentReference is empty");
        TypeToken<MonnifyBaseResponse<MandateDebitResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<MandateDebitResponse>>() {};

        try {
            return monnifyClient.get(
                    "/api/v1/direct-debit/mandate/debit-status?paymentReference=" + URLEncoder.encode(paymentReference, StandardCharsets.UTF_8.toString()),
                    null,
                    null,
                    typeToken
            );
        } catch (UnsupportedEncodingException e) {
            throw new MonnifyException(e);
        }
    }

    /**
     * Cancels an existing direct debit mandate.
     *
     * @param mandateCode The unique mandate code to be canceled.
     * @return A {@link MonnifyBaseResponse} containing a {@link MandateResponse}.
     * @author Oreoluwa Somuyiwa
     * @throws MonnifyValidationException If mandateCode is null or empty
     * @throws MonnifyException If encoding exception occurs
     */
    public MonnifyBaseResponse<MandateResponse> cancelMandate(String mandateCode) {

        if(StringUtils.isNullOrEmpty(mandateCode)) throw new MonnifyValidationException("mandateCode is empty");
        TypeToken<MonnifyBaseResponse<MandateResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<MandateResponse>>() {};

        try {
            return monnifyClient.patch(
                    "/api/v1/direct-debit/mandate/cancel-mandate/" + URLEncoder.encode(mandateCode, StandardCharsets.UTF_8.toString()),
                    "",
                    null,
                    null,
                    typeToken
            );
        } catch (UnsupportedEncodingException e) {
            throw new MonnifyException(e);
        }
    }
}
