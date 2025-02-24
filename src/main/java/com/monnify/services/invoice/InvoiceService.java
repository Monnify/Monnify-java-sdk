package com.monnify.services.invoice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monnify.exceptions.MonnifyException;
import com.monnify.exceptions.MonnifyValidationException;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.account.ReservedAccountResponse;
import com.monnify.models.invoice.InvoiceRequest;
import com.monnify.models.invoice.InvoiceResponse;
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
 * Service for handling Monnify invoice-related operations.
 *  Provides methods for creating, deleting and retrieving invoices.
 * @author Oreoluwa Somuyiwa
 */
public class InvoiceService {
    private static final Gson gson = new Gson();
    private static final MonnifyClient monnifyClient = new MonnifyClient();

    /**
     * Creates a new invoice.
     *
     * @param request The invoice request payload.
     * @return A {@link MonnifyBaseResponse} containing the created invoice details as {@link InvoiceResponse}
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<InvoiceResponse> createInvoice(InvoiceRequest request) {
        ValidationUtil.validate(request);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        TypeToken<MonnifyBaseResponse<InvoiceResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<InvoiceResponse>>() {};

        return monnifyClient.post(
                "/api/v1/invoice/create",
                gson.toJson(request),
                headers,
                null,
                typeToken);
    }

    /**
     * Retrieves details of a specific invoice.
     *
     * @param invoiceReference The reference of the invoice.
     * @return A {@link MonnifyBaseResponse} containing the created invoice details as {@link InvoiceResponse}
     * @author Oreoluwa Somuyiwa
     * @throws MonnifyValidationException If invoiceReference is null or empty
     * @throws MonnifyException If encoding exception occurs
     */
    public MonnifyBaseResponse<InvoiceResponse> viewInvoiceDetails(String invoiceReference) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        if(StringUtils.isNullOrEmpty(invoiceReference)) throw new MonnifyValidationException("Invoice reference is empty");
        TypeToken<MonnifyBaseResponse<InvoiceResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<InvoiceResponse>>() {};

        try {
            return monnifyClient.get(
                    "/api/v1/invoice/" + URLEncoder.encode(invoiceReference, StandardCharsets.UTF_8.toString()) + "/details",
                    headers,
                    null,
                    typeToken);
        } catch (UnsupportedEncodingException e) {
            throw new MonnifyException(e);
        }
    }

    /**
     * Retrieves a paginated list of all invoices.
     *
     * @param page The page number.
     * @param size The number of records per page.
     * @return A {@link MonnifyBaseResponse} containing a {@link SearchResponse} of invoices as {@link InvoiceResponse}.
     * @author Oreoluwa Somuyiwa
     */
    public MonnifyBaseResponse<SearchResponse<InvoiceResponse>> getAllInvoices(int page, int size) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        Map<String, String> parameters = new HashMap<>();
        parameters.put("page", String.valueOf(page));
        parameters.put("size", String.valueOf(size));

        TypeToken<MonnifyBaseResponse<SearchResponse<InvoiceResponse>>> typeToken =
                new TypeToken<MonnifyBaseResponse<SearchResponse<InvoiceResponse>>>() {};

        return monnifyClient.get(
                "/api/v1/invoice/all",
                headers,
                parameters,
                typeToken);
    }

    /**
     * Cancels an existing invoice.
     *
     * @param invoiceReference The reference of the invoice to cancel.
     * @return The cancelled invoice details.
     * @author Oreoluwa Somuyiwa
     * @throws MonnifyValidationException If invoiceReference is null or empty
     * @throws MonnifyException If encoding exception occurs
     */
    public MonnifyBaseResponse<InvoiceResponse> cancelInvoice(String invoiceReference) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());

        if(StringUtils.isNullOrEmpty(invoiceReference)) throw new MonnifyValidationException("Invoice reference is empty");
        TypeToken<MonnifyBaseResponse<InvoiceResponse>> typeToken =
                new TypeToken<MonnifyBaseResponse<InvoiceResponse>>() {};

        try {
            return monnifyClient.delete(
                    "/api/v1/invoice/" + URLEncoder.encode(invoiceReference, StandardCharsets.UTF_8.toString()) + "/cancel",
                    "",
                    headers,
                    null,
                    typeToken);
        } catch (UnsupportedEncodingException e) {
            throw new MonnifyException(e);
        }
    }
}
