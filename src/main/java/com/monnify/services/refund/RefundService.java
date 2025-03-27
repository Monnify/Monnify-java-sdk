package com.monnify.services.refund;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monnify.exceptions.MonnifyException;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.refund.RefundRequest;
import com.monnify.models.refund.RefundResponse;
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
 * The RefundService class provides methods to interact with the Monnify API
 * for initiating and managing refunds.
 * @author Oreoluwa Somuyiwa
 */
public class RefundService {
    private static final Gson gson = new Gson();
    private static final MonnifyClient monnifyClient = new MonnifyClient();

    /**
     * Initiates a refund using the provided refund request details.
     *
     * @param refundRequest The {@link RefundRequest} object containing the details required to initiate a refund.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the refund details in the {@link RefundResponse} object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<RefundResponse> initiateRefund(RefundRequest refundRequest) {
        ValidationUtil.validate(refundRequest);

        TypeToken<MonnifyBaseResponse<RefundResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<RefundResponse>>() {};

        return monnifyClient.post(
                "/api/v1/refunds/initiate-refund",
                gson.toJson(refundRequest),
                null,
                null,
                typeToken
        );
    }

    /**
     * Retrieves the status of a specific refund using its reference.
     *
     * @param refundReference The unique reference of the refund to retrieve.
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including the refund details in the {@link RefundResponse} object.
     * @throws MonnifyValidationException If refundReference is null or empty
     * @throws MonnifyException If an encoding error occurs while processing the request.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<RefundResponse> getRefundStatus(String refundReference) {

        if(StringUtils.isNullOrEmpty(refundReference)) throw new MonnifyValidationException("refundReference is empty");
        TypeToken<MonnifyBaseResponse<RefundResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<RefundResponse>>() {};

        try {
            return monnifyClient.get(
                    "/api/v1/refunds/" + URLEncoder.encode(refundReference, StandardCharsets.UTF_8.toString()),
                    null,
                    null,
                    typeToken
            );
        } catch (UnsupportedEncodingException e) {
            throw new MonnifyException(e);
        }
    }

    /**
     * Retrieves a list of refunds based on the specified pagination parameters.
     *
     * @param page The page number for pagination (optional).
     * @param size The number of items per page (optional).
     * @return A {@link MonnifyBaseResponse} object containing the response from the Monnify API,
     *         including a list of refund details in the {@link SearchResponse<RefundResponse> } object.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<SearchResponse<RefundResponse>> getRefunds(Integer page, Integer size) {

        Map<String, String> parameters = new HashMap<>();
        if(page != null) {
            parameters.put("page", page.toString());
        }
        if(size != null) {
            parameters.put("size", size.toString());
        }

        TypeToken<MonnifyBaseResponse<SearchResponse<RefundResponse>>> typeToken =
                new TypeToken<MonnifyBaseResponse<SearchResponse<RefundResponse>>>() {};

        return monnifyClient.get(
                "/api/v1/refunds",
                null,
                parameters,
                typeToken
        );
    }
}
