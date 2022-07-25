package com.example.linebot.replier;

import com.example.linebot.SystemCall;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;
import java.util.List;

public class Parrot implements Replier{
    private MessageEvent<TextMessageContent> event;

    public Parrot(MessageEvent<TextMessageContent> event) {
        this.event = event;
    }

   @Override
   public Message reply() {
       TextMessageContent tmc = this.event.getMessage();
       String text = tmc.getText();
       SystemCall systemCall = new SystemCall(text);
       String result = null;
       System.out.println(text);
       try {
           result = systemCall.result();
       } catch (IOException e) {
           e.printStackTrace();
       }

       return new TextMessage(result);
   }
}
