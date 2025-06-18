package com.monnify.models.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddLinkedAccountsRequest {
    private boolean getAllAvailableBanks;
    @Builder.Default
    private List<String> preferredBanks = new ArrayList<>();
}
