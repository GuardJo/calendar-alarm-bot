package com.guardjo.calendar.alarm.manager.domain.slack;

public record MessageText(String type, String text) {
    public static MessageText of(String type, String text) {
        return new MessageText(type, text);
    }
}
