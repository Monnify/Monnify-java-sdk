package com.monnify.models.verification;

import lombok.Data;

@Data
public class NINVerificationResponse {
    private String nin;
    private String lastName;
    private String firstName;
    private String middleName;
    private String dateOfBirth;
    private String gender;
    private String mobileNumber;
}
