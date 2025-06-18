package com.monnify.services.webhook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.monnify.exceptions.MonnifyException;
import com.monnify.models.webhook.*;
import com.monnify.utils.FlexibleDateDeserializer;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.Map;

/**
 * The {@link WebhookService} class provides utility methods for computing and validating
 * the Monnify webhook signature.
 * @author Oreoluwa Somuyiwa
 */
public class WebhookService {
    private static final String HMAC_SHA512 = "HmacSHA512";
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new FlexibleDateDeserializer())
            .create();

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    /**
     * Computes the HMAC-SHA512 hash of the provided data using the merchant's client secret.
     *
     * @param data                 The data to hash.
     * @param merchantClientSecret The merchant's secret key for the HMAC computation.
     * @return The computed HMAC-SHA512 hash as a hexadecimal string.
     * @throws SignatureException       If an error occurs during the signature computation.
     * @throws NoSuchAlgorithmException If the HMAC-SHA512 algorithm is not available.
     * @throws InvalidKeyException      If the provided key is invalid.
     * @author Oreoluwa Somuyiwa
     */
    public String calculateHMAC512TransactionHash(String data, String merchantClientSecret)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(merchantClientSecret.getBytes(), HMAC_SHA512);
        Mac mac = Mac.getInstance(HMAC_SHA512);
        mac.init(secretKeySpec);
        return toHexString(mac.doFinal(data.getBytes()));
    }

    /**
     * Validates if the received signature matches the computed signature.
     * The signature is computed using the {@link WebhookService#calculateHMAC512TransactionHash} method.
     *
     * @param receivedSignature    The signature received from the webhook.
     * @param data                 The data to hash.
     * @param merchantClientSecret The merchant's secret key for the HMAC computation.
     * @return {@code true} if the signatures match, otherwise {@code false}.
     * @author Oreoluwa Somuyiwa
     */
    public boolean isSignatureValid(String receivedSignature, String data, String merchantClientSecret) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        return receivedSignature.equals(calculateHMAC512TransactionHash(data, merchantClientSecret));
    }

    /**
     * Resolves and validates a Monnify webhook event by verifying the HMAC signature and
     * deserializing the payload into the appropriate event data structure based on the event type.
     *
     * <p>This method first ensures that the signature received matches the expected signature
     * calculated using the provided client secret. If the signature is invalid, it throws a
     * {@link MonnifyException}. If the signature is valid, it determines the event type from the
     * payload and deserializes it into a corresponding subclass of {@link BaseWebhookResponse}.
     *
     * @param receivedSignature The HMAC SHA512 signature received in the webhook header.
     * @param webhookPayload    The raw webhook payload as a map of key-value pairs.
     * @param merchantClientSecret The client secret used to validate the webhook signature.
     *
     * @throws MonnifyException if the signature is invalid, the event type is unrecognized,
     *                          or an error occurs during signature calculation.
     */
    public BaseWebhookResponse<?> resolveMonnifyWebhook(String receivedSignature, Map<String, Object> webhookPayload, String merchantClientSecret) {
        String payload = gson.toJson(webhookPayload);

        try {
            String calculated = calculateHMAC512TransactionHash(payload, merchantClientSecret);
            if (!receivedSignature.equals(calculated)) {
                throw new MonnifyException("Invalid signature received");
            }
        } catch (SignatureException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new MonnifyException("Unable to calculate webhook signature", e);
        }

        String eventType = (String) webhookPayload.get("eventType");

        switch (eventType) {
            case "SUCCESSFUL_TRANSACTION":
                return parse(payload, new TypeToken<BaseWebhookResponse<CollectionEventData>>() {
                });
            case "SUCCESSFUL_DISBURSEMENT":
            case "FAILED_DISBURSEMENT":
            case "REVERSED_DISBURSEMENT":
                return parse(payload, new TypeToken<BaseWebhookResponse<DisbursementEventData>>() {});
            case "SUCCESSFUL_REFUND":
            case "FAILED_REFUND":
                return parse(payload, new TypeToken<BaseWebhookResponse<RefundEventData>>() {});
            case "SETTLEMENT":
                return parse(payload, new TypeToken<BaseWebhookResponse<SettlementEventData>>() {});
            case "REJECTED_PAYMENT":
                return parse(payload, new TypeToken<BaseWebhookResponse<RejectedPaymentEventData>>() {});
            case "MANDATE_UPDATE":
                return parse(payload, new TypeToken<BaseWebhookResponse<MandateEventData>>() {});
            case "ACCOUNT_ACTIVITY":
                return parse(payload, new TypeToken<BaseWebhookResponse<WalletActivityEventData>>() {});
            default:
                throw new MonnifyException("Invalid event type: " + eventType);
        }
    }

    private <T> BaseWebhookResponse<T> parse(String json, TypeToken<BaseWebhookResponse<T>> typeToken) {
        return gson.fromJson(json, typeToken.getType());
    }
}
