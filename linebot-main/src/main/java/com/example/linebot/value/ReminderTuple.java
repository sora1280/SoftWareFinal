package com.example.linebot.value;

import java.rmi.RemoteException;
import java.time.LocalTime;

public class ReminderTuple {

    private final String userId;
    private final LocalTime pushAt;
    public final String pushText;

    public ReminderTuple(String userId, LocalTime pushAt, String pushText) {
        this.userId = userId;
        this.pushAt = pushAt;
        this.pushText = pushText;
    }

    public String getUserId() {
        return userId;
    }

    public LocalTime getPushAt() {
        return pushAt;
    }

    public String getPushText() {
        return pushText;
    }
}
