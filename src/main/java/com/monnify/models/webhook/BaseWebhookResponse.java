package com.monnify.models.webhook;

import lombok.Data;

@Data
public class BaseWebhookResponse<T> {
    private String eventType;
    private T eventData;
}
