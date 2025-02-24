package com.monnify.models.limitprofile;


import lombok.Data;

@Data
public class LimitProfileResponse {
    private String limitProfileCode;
    private String limitProfileName;
    private double singleTransactionValue;
    private int dailyTransactionVolume;
    private double dailyTransactionValue;
    private String dateCreated;
    private String lastModified;
}