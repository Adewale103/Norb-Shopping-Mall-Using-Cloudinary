package com.twinkles.Norbs_Shopping_Mall.service.sms;

import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.VerificationSmsRequest;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.responses.SmsResponse;

public interface SmsService {
    SmsResponse sendSms(VerificationSmsRequest smsRequest);
}
