package com.monnify.services.verification;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.verification.*;
import com.monnify.utils.MonnifyClient;
import com.monnify.utils.StringUtils;
import com.monnify.utils.ValidationUtil;

import java.util.HashMap;
import java.util.Map;

import static com.monnify.services.auth.AuthService.getToken;

/**
 * The {@link VerificationService} class provides methods to interact with the Monnify API
 * for validating bank accounts, verifying BVN (Bank Verification Number), and NIN (National Identification Number).
 * @author Oreoluwa Somuyiwa
 */
public class VerificationService {
    private static final Gson gson = new Gson();
    private static final MonnifyClient monnifyClient = new MonnifyClient();

    /**
     * Validates a bank account using the provided account number and bank code.
     *
     * @param accountNumber The bank account number to validate.
     * @param bankCode The bank code associated with the account.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the validation details in the {@link BankValidationResponse} object.
     * @throws MonnifyValidationException If accountNumber or bankCode is null or empty
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<BankValidationResponse> validateBankAccount(String accountNumber, String bankCode) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        if(StringUtils.isNullOrEmpty(accountNumber) || StringUtils.isNullOrEmpty(bankCode)) {
            throw new MonnifyValidationException("Account number or bank code is empty");
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("accountNumber", accountNumber);
        parameters.put("bankCode", bankCode);

        TypeToken<MonnifyBaseResponse<BankValidationResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<BankValidationResponse>>() {};

        return monnifyClient.get(
                "/api/v1/disbursements/account/validate",
                headers,
                parameters,
                typeToken
        );
    }

    /**
     * Verifies BVN (Bank Verification Number) information using the provided request details.
     *
     * @param bvnVerificationRequest The {@link BVNVerificationRequest} object containing the BVN details to verify.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the BVN verification details in the {@link BVNVerificationResponse} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<BVNVerificationResponse> verifyBVNInformation(BVNVerificationRequest bvnVerificationRequest) {
        ValidationUtil.validate(bvnVerificationRequest);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<BVNVerificationResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<BVNVerificationResponse>>() {};

        return monnifyClient.post(
                "/api/v1/vas/bvn-details-match",
                gson.toJson(bvnVerificationRequest),
                headers,
                null,
                typeToken
        );
    }

    /**
     * Matches a BVN (Bank Verification Number) with a bank account name using the provided request details.
     *
     * @param bvnAccountMatchRequest The {@link BVNAccountMatchRequest} object containing the BVN and account details to match.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the match details in the {@link BVNAccountMatchResponse} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<BVNAccountMatchResponse> bvnAccountNameMatch(BVNAccountMatchRequest bvnAccountMatchRequest) {
        ValidationUtil.validate(bvnAccountMatchRequest);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<BVNAccountMatchResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<BVNAccountMatchResponse>>() {};

        return monnifyClient.post(
                "/api/v1/vas/bvn-account-match",
                gson.toJson(bvnAccountMatchRequest),
                headers,
                null,
                typeToken
        );
    }

    /**
     * Verifies NIN (National Identification Number) information using the provided request details.
     *
     * @param ninVerificationRequest The {@link NINVerificationRequest} object containing the NIN details to verify.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the NIN verification details in the {@link NINVerificationResponse} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<NINVerificationResponse> verifyNIN(NINVerificationRequest ninVerificationRequest) {
        ValidationUtil.validate(ninVerificationRequest);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<NINVerificationResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<NINVerificationResponse>>() {};

        return monnifyClient.post(
                "/api/v1/vas/nin-details",
                gson.toJson(ninVerificationRequest),
                headers,
                null,
                typeToken
        );
    }
}
