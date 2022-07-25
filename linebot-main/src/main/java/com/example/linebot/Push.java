package com.example.linebot;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;

import com.linecorp.bot.model.response.BotApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import com.example.linebot.service.ReminderService;
import java.util.List;

@RestController
public class Push {

    private static final Logger log = LoggerFactory.getLogger(Push.class);

    private String userId = "U4282a4d50854be694d169a562789f5a6";

    private final LineMessagingClient messagingClient;

    public final ReminderService reminderService;

    @Autowired
    public Push(LineMessagingClient lineMessagingClient, ReminderService reminderService) {
        this.messagingClient = lineMessagingClient;
        this.reminderService = reminderService;
    }

    @GetMapping("test")
    public String hello(HttpServletRequest request) {
        return "Get from " + request.getRequestURL();
    }

    //時報をプッシュする
    @GetMapping("timetone") //<2>
    // @Scheduled(cron = "0 */1 * * * *", zone = "Asia/Tokyo") //<1>
    public String pushTimeTone() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("a K:mm"); //<3>
        String text = dtf.format(LocalDateTime.now());
        try {
            PushMessage pMsg =
                    new PushMessage(userId, new TextMessage(text)); //<4>
            BotApiResponse resp = messagingClient.pushMessage(pMsg).get();
            log.info("Sent messages: {}", resp);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return text; //<5>
    }

    @Scheduled(cron = "0 */1 * * * *", zone = "Asia/Tokyo")
    public void PushReminder() {
        try {
            List<PushMessage> messages = reminderService.doPushReminderItems(); //<1>
            for (PushMessage message : messages) {
                BotApiResponse resp = messagingClient.pushMessage((message)).get(); //<2>
                log.info("Sent messages: {}", resp);
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
