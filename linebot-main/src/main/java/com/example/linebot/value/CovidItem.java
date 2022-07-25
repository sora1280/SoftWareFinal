package com.example.linebot.value;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class CovidItem {

    private final List<CovidItemElement> itemList;

    @JsonCreator
    public CovidItem(List<CovidItemElement> itemList) {
        this.itemList = itemList;
    }

    public List<CovidItemElement> getItemList() {
        return itemList;
    }
}
