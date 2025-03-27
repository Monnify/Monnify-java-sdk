package com.monnify.models;

import lombok.Data;

@Data
public class MonnifyBaseResponse<T> {
    private boolean requestSuccessful;
    private String responseMessage;
    private String responseCode;
    private T responseBody;
}
