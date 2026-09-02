package com.monnify.mocktests;
import com.monnify.exceptions.MonnifyException;
import com.monnify.services.webhook.WebhookService;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class WebhookServiceMockTest {

    private final WebhookService webhookService = new WebhookService();

    @Test
    void shouldCalculateHMAC512TransactionHash() throws Exception {
        String data = "test-data";
        String merchantClientSecret = "test-secret";

        String result = webhookService.calculateHMAC512TransactionHash(
                data,
                merchantClientSecret
        );

        assertNotNull(result);
        assertEquals(128, result.length());
    }

    @Test
    void shouldReturnTrueWhenSignatureIsValid() throws Exception {
        String data = "test-data";
        String merchantClientSecret = "test-secret";

        String signature = webhookService.calculateHMAC512TransactionHash(
                data,
                merchantClientSecret
        );

        boolean result = webhookService.isSignatureValid(
                signature,
                data,
                merchantClientSecret
        );

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenSignatureIsInvalid() throws Exception {
        String data = "test-data";
        String merchantClientSecret = "test-secret";

        boolean result = webhookService.isSignatureValid(
                "invalid-signature",
                data,
                merchantClientSecret
        );

        assertFalse(result);
    }

    @Test
    void shouldResolveSuccessfulTransactionWebhook() throws Exception {
        Map<String, Object> webhookPayload = new HashMap<>();

        webhookPayload.put("eventType", "SUCCESSFUL_TRANSACTION");
        webhookPayload.put("eventData", new HashMap<String, Object>());

        String secret = "test-secret";

        String payload = new com.google.gson.Gson().toJson(webhookPayload);

        String signature = webhookService.calculateHMAC512TransactionHash(
                payload,
                secret
        );

        Object result = webhookService.resolveMonnifyWebhook(
                signature,
                webhookPayload,
                secret
        );

        assertNotNull(result);
        assertEquals(
                "SUCCESSFUL_TRANSACTION",
                ((com.monnify.models.webhook.BaseWebhookResponse<?>) result).getEventType()
        );
    }

    @Test
    void shouldResolveSuccessfulDisbursementWebhook() throws Exception {
        Map<String, Object> webhookPayload = new HashMap<>();

        webhookPayload.put("eventType", "SUCCESSFUL_DISBURSEMENT");
        webhookPayload.put("eventData", new HashMap<String, Object>());

        String secret = "test-secret";

        String payload = new com.google.gson.Gson().toJson(webhookPayload);

        String signature = webhookService.calculateHMAC512TransactionHash(
                payload,
                secret
        );

        Object result = webhookService.resolveMonnifyWebhook(
                signature,
                webhookPayload,
                secret
        );

        assertNotNull(result);
        assertEquals(
                "SUCCESSFUL_DISBURSEMENT",
                ((com.monnify.models.webhook.BaseWebhookResponse<?>) result).getEventType()
        );
    }

    @Test
    void shouldResolveFailedDisbursementWebhook() throws Exception {
        Map<String, Object> webhookPayload = new HashMap<>();

        webhookPayload.put("eventType", "FAILED_DISBURSEMENT");
        webhookPayload.put("eventData", new HashMap<String, Object>());

        String secret = "test-secret";

        String payload = new com.google.gson.Gson().toJson(webhookPayload);

        String signature = webhookService.calculateHMAC512TransactionHash(
                payload,
                secret
        );

        Object result = webhookService.resolveMonnifyWebhook(
                signature,
                webhookPayload,
                secret
        );

        assertNotNull(result);
        assertEquals(
                "FAILED_DISBURSEMENT",
                ((com.monnify.models.webhook.BaseWebhookResponse<?>) result).getEventType()
        );
    }

    @Test
    void shouldResolveReversedDisbursementWebhook() throws Exception {
        Map<String, Object> webhookPayload = new HashMap<>();

        webhookPayload.put("eventType", "REVERSED_DISBURSEMENT");
        webhookPayload.put("eventData", new HashMap<String, Object>());

        String secret = "test-secret";

        String payload = new com.google.gson.Gson().toJson(webhookPayload);

        String signature = webhookService.calculateHMAC512TransactionHash(
                payload,
                secret
        );

        Object result = webhookService.resolveMonnifyWebhook(
                signature,
                webhookPayload,
                secret
        );

        assertNotNull(result);
        assertEquals(
                "REVERSED_DISBURSEMENT",
                ((com.monnify.models.webhook.BaseWebhookResponse<?>) result).getEventType()
        );
    }

    @Test
    void shouldResolveSuccessfulRefundWebhook() throws Exception {
        Map<String, Object> webhookPayload = new HashMap<>();

        webhookPayload.put("eventType", "SUCCESSFUL_REFUND");
        webhookPayload.put("eventData", new HashMap<String, Object>());

        String secret = "test-secret";

        String payload = new com.google.gson.Gson().toJson(webhookPayload);

        String signature = webhookService.calculateHMAC512TransactionHash(
                payload,
                secret
        );

        Object result = webhookService.resolveMonnifyWebhook(
                signature,
                webhookPayload,
                secret
        );

        assertNotNull(result);
        assertEquals(
                "SUCCESSFUL_REFUND",
                ((com.monnify.models.webhook.BaseWebhookResponse<?>) result).getEventType()
        );
    }

    @Test
    void shouldResolveFailedRefundWebhook() throws Exception {
        Map<String, Object> webhookPayload = new HashMap<>();

        webhookPayload.put("eventType", "FAILED_REFUND");
        webhookPayload.put("eventData", new HashMap<String, Object>());

        String secret = "test-secret";

        String payload = new com.google.gson.Gson().toJson(webhookPayload);

        String signature = webhookService.calculateHMAC512TransactionHash(
                payload,
                secret
        );

        Object result = webhookService.resolveMonnifyWebhook(
                signature,
                webhookPayload,
                secret
        );

        assertNotNull(result);
        assertEquals(
                "FAILED_REFUND",
                ((com.monnify.models.webhook.BaseWebhookResponse<?>) result).getEventType()
        );
    }

    @Test
    void shouldResolveSettlementWebhook() throws Exception {
        Map<String, Object> webhookPayload = new HashMap<>();

        webhookPayload.put("eventType", "SETTLEMENT");
        webhookPayload.put("eventData", new HashMap<String, Object>());

        String secret = "test-secret";

        String payload = new com.google.gson.Gson().toJson(webhookPayload);

        String signature = webhookService.calculateHMAC512TransactionHash(
                payload,
                secret
        );

        Object result = webhookService.resolveMonnifyWebhook(
                signature,
                webhookPayload,
                secret
        );

        assertNotNull(result);
        assertEquals(
                "SETTLEMENT",
                ((com.monnify.models.webhook.BaseWebhookResponse<?>) result).getEventType()
        );
    }

    @Test
    void shouldResolveRejectedPaymentWebhook() throws Exception {
        Map<String, Object> webhookPayload = new HashMap<>();

        webhookPayload.put("eventType", "REJECTED_PAYMENT");
        webhookPayload.put("eventData", new HashMap<String, Object>());

        String secret = "test-secret";

        String payload = new com.google.gson.Gson().toJson(webhookPayload);

        String signature = webhookService.calculateHMAC512TransactionHash(
                payload,
                secret
        );

        Object result = webhookService.resolveMonnifyWebhook(
                signature,
                webhookPayload,
                secret
        );

        assertNotNull(result);
        assertEquals(
                "REJECTED_PAYMENT",
                ((com.monnify.models.webhook.BaseWebhookResponse<?>) result).getEventType()
        );
    }

    @Test
    void shouldResolveMandateUpdateWebhook() throws Exception {
        Map<String, Object> webhookPayload = new HashMap<>();

        webhookPayload.put("eventType", "MANDATE_UPDATE");
        webhookPayload.put("eventData", new HashMap<String, Object>());

        String secret = "test-secret";

        String payload = new com.google.gson.Gson().toJson(webhookPayload);

        String signature = webhookService.calculateHMAC512TransactionHash(
                payload,
                secret
        );

        Object result = webhookService.resolveMonnifyWebhook(
                signature,
                webhookPayload,
                secret
        );

        assertNotNull(result);
        assertEquals(
                "MANDATE_UPDATE",
                ((com.monnify.models.webhook.BaseWebhookResponse<?>) result).getEventType()
        );
    }

    @Test
    void shouldResolveAccountActivityWebhook() throws Exception {
        Map<String, Object> webhookPayload = new HashMap<>();

        webhookPayload.put("eventType", "ACCOUNT_ACTIVITY");
        webhookPayload.put("eventData", new HashMap<String, Object>());

        String secret = "test-secret";

        String payload = new com.google.gson.Gson().toJson(webhookPayload);

        String signature = webhookService.calculateHMAC512TransactionHash(
                payload,
                secret
        );

        Object result = webhookService.resolveMonnifyWebhook(
                signature,
                webhookPayload,
                secret
        );

        assertNotNull(result);
        assertEquals(
                "ACCOUNT_ACTIVITY",
                ((com.monnify.models.webhook.BaseWebhookResponse<?>) result).getEventType()
        );
    }

    @Test
    void shouldThrowExceptionWhenWebhookSignatureIsInvalid() {
        Map<String, Object> webhookPayload = new HashMap<>();

        webhookPayload.put("eventType", "SETTLEMENT");
        webhookPayload.put("eventData", new HashMap<String, Object>());

        String secret = "test-secret";

        MonnifyException exception = assertThrows(
                MonnifyException.class,
                () -> webhookService.resolveMonnifyWebhook(
                        "invalid-signature",
                        webhookPayload,
                        secret
                )
        );

        assertEquals("Invalid signature received", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidEventType() throws Exception {
        Map<String, Object> webhookPayload = new HashMap<>();

        webhookPayload.put("eventType", "INVALID_EVENT");
        webhookPayload.put("eventData", new HashMap<String, Object>());

        String secret = "test-secret";

        String payload = new com.google.gson.Gson().toJson(webhookPayload);

        String signature = webhookService.calculateHMAC512TransactionHash(
                payload,
                secret
        );

        MonnifyException exception = assertThrows(
                MonnifyException.class,
                () -> webhookService.resolveMonnifyWebhook(
                        signature,
                        webhookPayload,
                        secret
                )
        );

        assertEquals(
                "Invalid event type: INVALID_EVENT",
                exception.getMessage()
        );
    }

}
