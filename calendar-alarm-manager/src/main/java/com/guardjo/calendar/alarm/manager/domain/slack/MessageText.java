package com.guardjo.calendar.alarm.manager.domain.slack;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MessageText(String type, String text) {
    public static MessageText of(String type, String text) {
        return new MessageText(type, text);
    }
}
