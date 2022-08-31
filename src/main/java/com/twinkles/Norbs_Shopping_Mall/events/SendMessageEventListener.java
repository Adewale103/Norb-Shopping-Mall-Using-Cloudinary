package com.twinkles.Norbs_Shopping_Mall.events;


import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.VerificationSmsRequest;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.responses.SmsResponse;
import com.twinkles.Norbs_Shopping_Mall.service.sms.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SendMessageEventListener {
    @Qualifier("twilio_sender")
    private final SmsService smsService;

    public SendMessageEventListener(SmsService smsService) {
        this.smsService = smsService;
    }


    @EventListener
    public void handleSendMessageEvent(SendMessageEvent event){
        VerificationSmsRequest messageRequest = (VerificationSmsRequest) event.getSource();

        String verificationLink = messageRequest.getDomainUrl()+"api/v1/auth/verify/"+messageRequest.getVerificationToken();

        log.info("Message request --> {}",messageRequest);

        String body = String.format("%s%s%s%s", "Hello ",messageRequest.getAppUserFullName(),". Kindly click the link below to verify your NSM account",verificationLink);
        messageRequest.setMessage(body);
        SmsResponse smsResponse = smsService.sendSms(messageRequest);
        log.info("Mail Response simple --> {}", smsResponse);
        }
    }

