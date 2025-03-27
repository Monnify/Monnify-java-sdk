package com.monnify.services.bank;

import com.google.gson.reflect.TypeToken;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.bank.BankResponse;
import com.monnify.utils.MonnifyClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.monnify.services.auth.AuthService.getToken;

/**
 * Service for retrieving bank-related information from the Monnify API.
 * Provides methods to fetch the list of banks and banks with USSD short codes.
 * @author Oreoluwa Somuyiwa
 */
public class BankService {
    private static final MonnifyClient monnifyClient = new MonnifyClient();

    /**
     * Retrieves a list of all banks available on the Monnify platform.
     *
     * @return A {@link MonnifyBaseResponse} containing a list of {@link BankResponse} objects.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<List<BankResponse>> getBanks() {
        TypeToken<MonnifyBaseResponse<List<BankResponse>>> typeToken =
                new TypeToken<MonnifyBaseResponse<List<BankResponse>>>() {};

        return monnifyClient.get(
                "/api/v1/banks",
                null,
                null,
                typeToken
        );
    }

    /**
     * Retrieves a list of banks that support USSD short codes for transactions.
     *
     * @return A {@link MonnifyBaseResponse} containing a list of {@link BankResponse} objects.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<List<BankResponse>> getBanksWithUssdShortCode() {
        TypeToken<MonnifyBaseResponse<List<BankResponse>>> typeToken =
                new TypeToken<MonnifyBaseResponse<List<BankResponse>>>() {};

        return monnifyClient.get(
                "/api/v1/sdk/transactions/banks",
                null,
                null,
                typeToken
        );
    }
}

