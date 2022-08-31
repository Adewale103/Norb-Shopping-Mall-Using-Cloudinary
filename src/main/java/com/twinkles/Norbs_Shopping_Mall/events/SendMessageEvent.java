package com.twinkles.Norbs_Shopping_Mall.events;

import org.springframework.context.ApplicationEvent;

public class SendMessageEvent extends ApplicationEvent {
    public SendMessageEvent(Object source){super(source);}
}
