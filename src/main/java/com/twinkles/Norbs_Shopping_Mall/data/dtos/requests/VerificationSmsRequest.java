package com.twinkles.Norbs_Shopping_Mall.data.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class VerificationSmsRequest {
    private String receiverPhoneNumber;
    private String message;
    private String domainUrl;
    private String verificationToken;
    private String appUserFullName;

}
