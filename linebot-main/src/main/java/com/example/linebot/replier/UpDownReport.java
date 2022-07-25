package com.example.linebot.replier;

import com.example.linebot.value.CovidItem;
import com.example.linebot.value.CovidItemElement;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.time.LocalDate;
import java.util.List;

public class UpDownReport implements Replier {

    private static final String MESSAGE_FORMAT = "%s曜日の%sの新規感染者数はその前の週から%d人変化しました\n増加率は%.2f％です";

    private final CovidItem item;

    private String dayOfWeek;

    private int DOWnum;

    public UpDownReport(CovidItem item, String dayOfWeek) {
        this.item = item;
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public Message reply() {
        String body = "データがありません";
        List<CovidItemElement> list = item.getItemList();
        if(list.size() > 0) {
            body = getMessageOfLast();
        }
        return new TextMessage(body);
    }

    private int change(String dayOfWeek) {
        switch (dayOfWeek) {
            case "月":
                return 1;
            case "火":
                return 2;
            case "水":
                return 3;
            case "木":
                return 4;
            case "金":
                return 5;
            case "土":
                return 6;
            case "日":
                return 7;
            default:
                throw new IllegalStateException("該当する曜日がありません");
        }

    }
    private String getMessageOfLast() {
        List<CovidItemElement> list = item.getItemList();
        CovidItemElement atLast = list.get(0); //最新データ
        LocalDate date = atLast.getDate();
        int DOW = change(dayOfWeek);  //求めたい曜日
        int nowDOW = date.getDayOfWeek().getValue(); //今の曜日

        if(nowDOW >= DOW) {
            DOWnum = nowDOW - DOW;
        } else {
            DOWnum = 7 + nowDOW - DOW;
        }

        CovidItemElement lastWeek = list.get(DOWnum); //一番近い求めたい曜日
        CovidItemElement WBL = list.get(DOWnum + 7); //その前の週

        String region = lastWeek.getNameJp();
        int upDown = lastWeek.getNpatients() - WBL.getNpatients();

        float lSum = 0;
        float wSum = 0;
        for(int i = 0; i < 7; i++) {
            CovidItemElement lastWeeks = list.get(i);
            CovidItemElement lastWeeks2 = list.get(i+1);
            lSum = lSum + lastWeeks.getNpatients() - lastWeeks2.getNpatients();
        }

        for(int j = 7; j < 14; j++) {
            CovidItemElement weekBeforeLast = list.get(j);
            CovidItemElement weekBeforeLast2 = list.get(j+1);
            wSum = wSum + weekBeforeLast.getNpatients() - weekBeforeLast2.getNpatients();
        }
        float increase = (lSum / wSum) * 100;


        return String.format(MESSAGE_FORMAT, dayOfWeek, region, upDown, increase);
    }
}
