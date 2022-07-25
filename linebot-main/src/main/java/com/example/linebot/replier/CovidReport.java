package com.example.linebot.replier;

import com.example.linebot.value.CovidItem;
import com.example.linebot.value.CovidItemElement;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class CovidReport implements Replier {

    private static final String MESSAGE_FORMAT = "%s の %s 月 %s 日時点の感染者数は %d 人";

    private final CovidItem item;

    public CovidReport(CovidItem item) {
        this.item = item;
    }

    @Override
    public Message reply() {
        String body = "データがありません";
        List<CovidItemElement> list = item.getItemList(); //(1)
        if (list.size() > 0) {
            body = getMessageOfLast();
        }
        return new TextMessage(body);
    }

    private String getMessageOfLast() {
        List<CovidItemElement> list = item.getItemList();
        CovidItemElement atLast = list.get(0); //(2)
        LocalDate date = atLast.getDate(); //(3)
        int month = date.getMonthValue(); //(4)
        int dayOfMonth = date.getDayOfMonth(); //(5)
        String region = atLast.getNameJp(); //(6)
        int npatients = atLast.getNpatients(); //(7)
        return String.format(
                MESSAGE_FORMAT, region, month, dayOfMonth,npatients); //(8)
    }
}
