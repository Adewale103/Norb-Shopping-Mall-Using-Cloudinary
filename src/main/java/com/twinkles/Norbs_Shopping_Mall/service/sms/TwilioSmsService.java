package com.twinkles.Norbs_Shopping_Mall.service.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.VerificationSmsRequest;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.responses.SmsResponse;
import com.twinkles.Norbs_Shopping_Mall.web.exception.NorbsShoppingMallException;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("twilio_sender")
public class TwilioSmsService implements SmsService {
    private final String ACCOUNT_SID;
    private final String AUTH_TOKEN;
    private final String TRIAL_NUMBER;

    public TwilioSmsService() {
        ACCOUNT_SID = System.getenv("ACCOUNT_SID");
        AUTH_TOKEN = System.getenv("AUTH_TOKEN");
        TRIAL_NUMBER = System.getenv("TRIAL_NUMBER");
    }

    @Override
    public SmsResponse sendSms(VerificationSmsRequest smsRequest) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        if(isPhoneNumberValid(smsRequest.getReceiverPhoneNumber())){
        PhoneNumber to = new PhoneNumber(smsRequest.getReceiverPhoneNumber());
        PhoneNumber from = new PhoneNumber(TRIAL_NUMBER);
        String message = smsRequest.getMessage();
        MessageCreator creator = Message.creator(to,from,message);
        creator.create();
            return new SmsResponse(true);
        }
        else {
            throw new NorbsShoppingMallException("Phone number ["+smsRequest.getReceiverPhoneNumber()+
                    "]is not a valid number!");
        }
    }

    private boolean isPhoneNumberValid(String receiverPhoneNumber) {
        String regex = "(?:(?:(?:\\+?234(?:\\h1)?|01)\\h*)?(?:\\(\\d{3}\\)|\\d{3})|\\d{4})(?:\\W*\\d{3})?\\W*\\d{4}(?!\\d)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(receiverPhoneNumber);
        return matcher.matches();
    }
}
