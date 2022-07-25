package com.example.linebot;

import com.example.linebot.replier.*;
import com.example.linebot.repository.ReminderRepository;
import com.example.linebot.service.CovidGovService;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.linebot.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Callable;

@LineMessageHandler
public class Callback {
    private static final Logger log = LoggerFactory.getLogger(Callback.class);
    private final ReminderService reminderService;
    private final CovidGovService covidGovService;

    @Autowired
    public Callback(ReminderService reminderService, CovidGovService covidGovService) {
        this.reminderService = reminderService;
        this.covidGovService = covidGovService;
    }
//    @EventMapping
//    public Message handleFollow(FollowEvent event) {
//        Follow follow = new Follow(event);
//        return follow.reply();
//    }
//
//    @EventMapping
//    public Message handleMessage(MessageEvent<TextMessageContent> event) {
//        Parrot parrot = new Parrot(event);
//        return parrot.reply();
//    }

    @EventMapping
    public Message handleMessage(MessageEvent<TextMessageContent> event) {
        TextMessageContent tmc = event.getMessage();
        String text = tmc.getText();
        Intent intent = Intent.whiciIntent(text);
        switch (intent) {
            case REMINDER:
                RemindOn remindOn = reminderService.doReplyOfNewItem(event);
                return remindOn.reply();
            case COVID_TOTAL:
                CovidReport covidReport = covidGovService.doReplyWithCovid(event);
                return covidReport.reply();
            case COVID_UPDOWN:
                UpDownReport upDownReport = covidGovService.doReplyCovidUpDown(event);
                return upDownReport.reply();
            case UNKNOWN:
            default:Parrot parrot = new Parrot(event);
            return parrot.reply();
        }
    }
}
