package com.monnify.services.limitprofile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monnify.exceptions.MonnifyException;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.limitprofile.LimitProfileRequest;
import com.monnify.models.limitprofile.LimitProfileResponse;
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
 * Service for handling Monnify limit profile-related operations.
 * Provides methods for creating, updating and deleting limit profiles.
 * @author Oreoluwa Somuyiwa
 */
public class LimitProfileService {
    private static final Gson gson = new Gson();
    private static final MonnifyClient monnifyClient = new MonnifyClient();

    /**
     * Creates a new limit profile.
     *
     * @param request The limit profile request payload.
     * @return A {@link MonnifyBaseResponse} containing the created limit profile details.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<LimitProfileResponse> createLimitProfile(LimitProfileRequest request) {
        ValidationUtil.validate(request);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<LimitProfileResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<LimitProfileResponse>>() {};

        return monnifyClient.post(
                "/api/v1/limit-profile",
                gson.toJson(request),
                headers,
                null,
                typeToken);
    }

    /**
     * Retrieves all limit profiles.
     *
     * @return A {@link MonnifyBaseResponse} containing a list of limit profiles.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<SearchResponse<LimitProfileResponse>> getLimitProfiles() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<SearchResponse<LimitProfileResponse>>> typeToken =
                new TypeToken<MonnifyBaseResponse<SearchResponse<LimitProfileResponse>>>() {};

        return monnifyClient.get(
                "/api/v1/limit-profile",
                headers,
                null,
                typeToken);
    }

    /**
     * Updates an existing limit profile.
     *
     * @param limitProfileCode The code of the limit profile to update.
     * @param request The updated limit profile request payload.
     * @return A {@link MonnifyBaseResponse} containing the updated limit profile details.
     * @author Oreoluwa Somuyiwa
     * @throws MonnifyValidationException If limitProfileCode is null or empty
     * @throws MonnifyException If encoding exception occurs
     */
    public MonnifyBaseResponse<LimitProfileResponse> updateLimitProfile(String limitProfileCode, LimitProfileRequest request) {
        ValidationUtil.validate(request);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        if(StringUtils.isNullOrEmpty(limitProfileCode)) throw new MonnifyValidationException("Invoice reference is empty");
        TypeToken<MonnifyBaseResponse<LimitProfileResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<LimitProfileResponse>>() {};

        try {
            return monnifyClient.put(
                    "/api/v1/limit-profile/" + URLEncoder.encode(limitProfileCode, StandardCharsets.UTF_8.toString()),
                    gson.toJson(request),
                    headers,
                    null,
                    typeToken);
        } catch (UnsupportedEncodingException e) {
            throw new MonnifyException(e);
        }
    }
}
