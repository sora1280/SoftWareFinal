package com.example.linebot.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.DayOfWeek;
import java.time.LocalDate;

//スネークケース abc_deのキーとキャメルケース abcDeのフィールド名をマッピングする
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CovidItemElement {

    private final LocalDate date;
    private final String nameJp;
    private final int npatients;
    //private final DayOfWeek dayOfWeek;

    @JsonCreator
    public CovidItemElement(LocalDate date, String nameJp, int npatients, DayOfWeek dayOfWeek) {
        this.date = date;
        this.nameJp = nameJp;
        this.npatients = npatients;
        //this.dayOfWeek = dayOfWeek;
    }

    public LocalDate getDate() {
        return date;
    }

//    public DayOfWeek getDayOfWeek() {
//        return date.getDayOfWeek();
//    }

    public String getNameJp() {
        return nameJp;
    }

    public int getNpatients() {
        return npatients;
    }
}
