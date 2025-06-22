# Monnify Java SDK

A Java client library for interacting with the [Monnify API](https://developers.monnify.com). This SDK simplifies the process of integrating Monnify into Java applications, providing methods to create and manage reserved accounts, transactions, disbursements, and more. All Monnify APIs are implemented, feel free to also check the test folder for the implementations of each service. 

## Features
- Reserved Account Service
- Transaction Service
- Disbursement Service
- Verification Service
- Sub Account Service
- Refund Service
- Invoice Service
- Direct Debit Service
- All reqeust DTOs have builders for simplicity
- Extensive error handling

## Installation
Add the following dependency to your `pom.xml` if using Maven:

```xml
<dependency>
    <groupId>com.monnify</groupId>
    <artifactId>monnify-java-sdk</artifactId>
    <version>1.1.0</version>
</dependency>
```

For Gradle:

```gradle
dependencies {
    implementation 'com.monnify:monnify-java:1.1.0'
}
```

## Getting Started
### 1. Initialize the Monnify Client
Before making API calls, initialize the Monnify client with your API credentials (please note this can only be done once):

```java
import com.monnify.Monnify;

Monnify.initialize("YOUR_API_KEY", "YOUR_SECRET_KEY");
```

You can also use environment variables:

```java
Monnify.initialize(System.getenv("apiKey"), System.getenv("secretKey"));
```

### 2. Create a Reserved Account

```java
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.account.ReservedAccountRequest;
import com.monnify.models.account.ReservedAccountResponse;
import com.monnify.services.reservedaccount.ReservedAccountService;
import java.util.Arrays;
import java.util.UUID;

// The rest of your code

ReservedAccountService reservedAccountService = new ReservedAccountService();
ReservedAccountRequest request = new ReservedAccountRequest();
request.setAccountReference(UUID.randomUUID().toString());
request.setAccountName("Test Reserved Account");
request.setCurrencyCode("NGN");
request.setContractCode("YOUR_CONTRACT_CODE");
request.setCustomerEmail("customer@example.com");
request.setCustomerName("John Doe");
request.setBvn("21212121212");
request.setNin("01212121212");
request.setGetAllAvailableBanks(true);

MonnifyBaseResponse<ReservedAccountResponse> response = reservedAccountService.createReservedAccount(request);
if (response.isRequestSuccessful()) {
    System.out.println("Reserved Account Created");
}
```

### 3. Retrieve Reserved Account Details

```java
MonnifyBaseResponse<ReservedAccountResponse> response = reservedAccountService.getReservedAccountDetails("account-reference");
if (response.isRequestSuccessful()) {
    System.out.println("Account Found: " + response.toString());
}
```

### 4. Deallocate a Reserved Account

```java
MonnifyBaseResponse<ReservedAccountResponse> response = reservedAccountService.deallocateReservedAccount("account-reference");
if (response.isRequestSuccessful()) {
    System.out.println("Account deallocated successfully");
}
```

### 5. Disbursement Services
#### a. Batch Disbursement
```java
import com.monnify.models.disbursement.BatchDisbursementRequest;
import com.monnify.models.disbursement.DisbursementTransaction;
import com.monnify.models.disbursement.OnValidationFailure;
import com.monnify.services.disbursement.DisbursementService;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

DisbursementService disbursementService = new DisbursementService();
BatchDisbursementRequest batchRequest = BatchDisbursementRequest.builder()
        .batchReference(UUID.randomUUID().toString())
        .title("SDK Test Batch")
        .narration("Test batch on SDK")
        .notificationInterval(25)
        .onValidationFailure(OnValidationFailure.CONTINUE)
        .sourceAccountNumber("7853297429")
        .transactionList(Arrays.asList(
            DisbursementTransaction.builder()
                .amount(BigDecimal.valueOf(1000))
                .currency("NGN")
                .narration("Batch disbursement 1")
                .reference(UUID.randomUUID().toString())
                .destinationAccountNumber("0100030292")
                .destinationBankCode("044")
                .build()
        ))
        .build();

disbursementService.disburseBatch(batchRequest);
```

### 6. Transaction Service
#### a. Initializing a Transaction

```java
import com.monnify.Monnify;
import com.monnify.models.MonnifyBaseResponse;
import com.monnify.models.SearchResponse;
import com.monnify.models.transaction.*;

import java.math.BigDecimal;
import java.util.*;

// the rest of your code
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(BigDecimal.valueOf(20L));
        transactionRequest.setCustomerName("Stephen Ikhane");
        transactionRequest.setCustomerEmail("stephen@ikhane.com");
        transactionRequest.setPaymentReference(UUID.randomUUID().toString());
        transactionRequest.setPaymentDescription("Trial transaction");
        transactionRequest.setCurrencyCode("NGN");
        transactionRequest.setContractCode("YOUR_CONTRACT_CODE");
        transactionRequest.setRedirectURL("https://my-merchants-page.com/transaction/confirm");
        transactionRequest.setPaymentMethods(Arrays.asList(PaymentMethod.CARD, PaymentMethod.ACCOUNT_TRANSFER));

        IncomeSplitConfig incomeSplitConfig = new IncomeSplitConfig();
        incomeSplitConfig.setSubAccountCode("MFY_SUB_523071569011");
        incomeSplitConfig.setSplitAmount(0);
        incomeSplitConfig.setFeeBearer(false);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("name", "Ore Sho");
        metadata.put("Balance", 100000);
        metadata.put("CurrencyCode", "NGN");
        metadata.put("MerchantName", "Stephen Ikhane");
        transactionRequest.setMetaData(metadata);

        transactionRequest.setIncomeSplitConfig(Collections.singletonList(incomeSplitConfig));
        TransactionService transactionService = new TransactionService();
        MonnifyBaseResponse<TransactionResponse> response = transactionService.initializeTransaction(transactionRequest);
        
```

## License
This library is open-source and available under the MIT License.

## Contributing
Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

## Support
For issues or inquiries, contact [integration-support@monnify.com](mailto:integration-support@monnify.com) or visit the [Monnify API Documentation](https://developers.monnify.com).

