package com.monnify.services.webhook;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;

/**
 * The {@link WebhookService} class provides utility methods for computing and validating
 * the Monnify webhook signature.
 * @author Oreoluwa Somuyiwa
 */
public class WebhookService {
    private static final String HMAC_SHA512 = "HmacSHA512";

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
     * @param data The data to hash.
     * @param merchantClientSecret The merchant's secret key for the HMAC computation.
     * @return The computed HMAC-SHA512 hash as a hexadecimal string.
     * @throws SignatureException If an error occurs during the signature computation.
     * @throws NoSuchAlgorithmException If the HMAC-SHA512 algorithm is not available.
     * @throws InvalidKeyException If the provided key is invalid.
     * @author Oreoluwa Somuyiwa
     */
    public String calculateHMAC512TransactionHash(String data, String merchantClientSecret)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
    {
        SecretKeySpec secretKeySpec = new SecretKeySpec(merchantClientSecret.getBytes(), HMAC_SHA512);
        Mac mac = Mac.getInstance(HMAC_SHA512);
        mac.init(secretKeySpec);
        return toHexString(mac.doFinal(data.getBytes()));
    }

    /**
     * Validates if the received signature matches the computed signature.
     *
     * @param receivedSignature The signature received from the webhook.
     * @param computedSignature The signature computed using the {@link WebhookService#calculateHMAC512TransactionHash} method.
     * @return {@code true} if the signatures match, otherwise {@code false}.
     * @author Oreoluwa Somuyiwa
     */
    public boolean isSignatureValid(String receivedSignature, String computedSignature){
        return receivedSignature.equals(computedSignature);
    }
}
