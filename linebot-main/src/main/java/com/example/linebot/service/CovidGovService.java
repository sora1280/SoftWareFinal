package com.example.linebot.service;

import com.example.linebot.replier.CovidReport;
import com.example.linebot.replier.UpDownReport;
import com.example.linebot.repository.CovidGovRepository;
import com.example.linebot.value.CovidItem;
import com.example.linebot.value.CovidSlot;
import com.example.linebot.value.UpDownSlot;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CovidGovService {

    private final CovidGovRepository covidGovRepository;

    @Autowired
    public CovidGovService(CovidGovRepository covidGovRepository) {
        this.covidGovRepository = covidGovRepository;
    }

    public CovidReport doReplyWithCovid(MessageEvent<TextMessageContent> event) {
        TextMessageContent tmc = event.getMessage();
        String text = tmc.getText();
        CovidSlot covidSlot = new CovidSlot(text); //(1)
        String region = covidSlot.getRegion();
        CovidItem covidItem = covidGovRepository.findCovidGovAPI(region); //(2)
        return new CovidReport(covidItem); //(3)
    }

    public UpDownReport doReplyCovidUpDown(MessageEvent<TextMessageContent> event) {
        TextMessageContent tmc = event.getMessage();
        String text = tmc.getText();
        UpDownSlot upDownSlot = new UpDownSlot(text);
        String region = upDownSlot.getRegion();
        CovidItem covidItem = covidGovRepository.findCovidGovAPI(region);
        String deyOfWeek = upDownSlot.getDayOfWeek();
        return new UpDownReport(covidItem, deyOfWeek);

    }
}
