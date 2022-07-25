package com.example.linebot.service;

import com.example.linebot.replier.RemindOn;
import com.example.linebot.value.ReminderItem;
import com.example.linebot.value.ReminderSlot;
import com.example.linebot.repository.ReminderRepository;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.linebot.value.ReminderTuple;
import com.linecorp.bot.model.PushMessage;
import java.util.List;

@Service
public class ReminderService {

    private final ReminderRepository repository;

    @Autowired
    public ReminderService(ReminderRepository reminderRepository) {
        this.repository = reminderRepository;
    }

    public RemindOn doReplyOfNewItem(MessageEvent<TextMessageContent> event) {
        String userId = event.getSource().getUserId();
        TextMessageContent tmc = event.getMessage();
        String text = tmc.getText();
        ReminderSlot slot = new ReminderSlot(text);
        ReminderItem item = new ReminderItem(userId, slot);

        repository.insert(item);

        return new RemindOn(text);
    }

    public List<PushMessage> doPushReminderItems() {
        List<ReminderTuple> ReminderTuples = repository.findPreviousItems(); //<1>
        List<PushMessage> pushMessages = ReminderTuples.stream().map(tuple -> toPushMessage(tuple)).toList(); //<2>

        repository.deletePreviousItems();

        return pushMessages;
    }

    private PushMessage toPushMessage(ReminderTuple tuple) {
        String userId = tuple.getUserId();
        String pushText = tuple.getPushText();
        String body = String.format("%s の時間です!", pushText);

        return new PushMessage(userId, new TextMessage(body));
    }
}
