package com.guardjo.calendar.alarm.manager.domain.slack;

public record MessageBlock(String type, MessageText text) {
    public static MessageBlock of(String type, MessageText text) {
        return new MessageBlock(type, text);
    }
}
