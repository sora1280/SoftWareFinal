package com.example.linebot.value;

import com.example.linebot.replier.Intent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpDownSlot {

    private final String region;
    private final String dayOfWeek;

    public UpDownSlot(String text) {
        String regexp = Intent.COVID_UPDOWN.getRegexp();
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            region = matcher.group(1);
            dayOfWeek = matcher.group(2); //動く?
        } else {
            //正規表現にマッチしない場合、実行時例外をthrowする
            throw new IllegalArgumentException("Text をスロットに分けられません");
        }
    }

    public String getRegion() {
        return region;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }
}
