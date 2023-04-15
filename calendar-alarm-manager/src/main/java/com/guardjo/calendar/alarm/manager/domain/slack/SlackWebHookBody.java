package com.guardjo.calendar.alarm.manager.domain.slack;

public record SlackWebHookBody(
        String text
) {
    public static SlackWebHookBody of(String text) {
        return new SlackWebHookBody(text);
    }
}
