package com.monnify.services.wallet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.wallet.WalletBalanceResponse;
import com.monnify.models.wallet.WalletRequest;
import com.monnify.models.wallet.WalletResponse;
import com.monnify.models.wallet.WalletTransactionResponse;
import com.monnify.utils.MonnifyClient;
import com.monnify.utils.StringUtils;
import com.monnify.utils.ValidationUtil;

import java.util.HashMap;
import java.util.Map;

import static com.monnify.services.auth.AuthService.getToken;

/**
 * The {@link WalletService} class provides methods to interact with the Monnify API
 * for creating wallets, retrieving wallet balances, and managing wallet transactions.
 * @author Oreoluwa Somuyiwa
 */
public class WalletService {
    private static final Gson gson = new Gson();
    private static final MonnifyClient monnifyClient = new MonnifyClient();

    /**
     * Creates a wallet using the provided request details.
     *
     * @param request The {@link WalletRequest} object containing the details required to create a wallet.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the wallet details in the {@link WalletResponse} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<WalletResponse> createWallet(WalletRequest request) {
        ValidationUtil.validate(request);


        TypeToken<MonnifyBaseResponse<WalletResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<WalletResponse>>() {};

        return monnifyClient.post(
                "/api/v1/disbursements/wallet",
                gson.toJson(request),
                null,
                null,
                typeToken);
    }

    /**
     * Retrieves the balance of a specific wallet using its account number.
     *
     * @param accountNumber The account number of the wallet to retrieve the balance for.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the wallet balance details in the {@link WalletBalanceResponse} object.
     * @throws MonnifyValidationException If accountNumber is null or empty
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<WalletBalanceResponse> getWalletBalance(String accountNumber) {

        if(StringUtils.isNullOrEmpty(accountNumber)) throw new MonnifyValidationException("account number is required");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("accountNumber", accountNumber);

        TypeToken<MonnifyBaseResponse<WalletBalanceResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<WalletBalanceResponse>>() {};

        return monnifyClient.get(
                "/api/v1/disbursements/wallet/balance",
                null,
                parameters,
                typeToken);
    }

    /**
     * Retrieves a list of wallets based on the specified pagination parameters and customer email.
     *
     * @param pageNo The page number for pagination, 0-index.
     * @param pageSize The number of items per page.
     * @param customerEmail The email of the customer associated with the wallets (optional).
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including a list of wallets in the {@link SearchResponse<WalletResponse>} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<SearchResponse<WalletResponse>> getAllWallets(int pageNo, int pageSize, String customerEmail) {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("pageSize", String.valueOf(pageSize));
        parameters.put("pageNo", String.valueOf(pageNo));
        parameters.put("customerEmail", customerEmail);

        TypeToken<MonnifyBaseResponse<SearchResponse<WalletResponse>>> typeToken =
                new TypeToken<MonnifyBaseResponse<SearchResponse<WalletResponse>>>() {};

        return monnifyClient.get(
                "/api/v1/disbursements/wallet",
                null,
                parameters,
                typeToken);
    }

    /**
     * Retrieves a list of transactions for a specific wallet using its account number.
     *
     * @param accountNumber The account number of the wallet to retrieve transactions for.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including a list of transactions in the {@link SearchResponse<WalletTransactionResponse>} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<SearchResponse<WalletTransactionResponse>> getWalletTransactions(String accountNumber) {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("accountNumber", String.valueOf(accountNumber));

        TypeToken<MonnifyBaseResponse<SearchResponse<WalletTransactionResponse>>> typeToken =
                new TypeToken<MonnifyBaseResponse<SearchResponse<WalletTransactionResponse>>>() {};

        return monnifyClient.get(
                "/api/v1/disbursements/wallet/transactions",
                null,
                parameters,
                typeToken);
    }

}
