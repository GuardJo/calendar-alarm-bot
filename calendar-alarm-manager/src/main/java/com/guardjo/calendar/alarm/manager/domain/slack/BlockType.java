package com.guardjo.calendar.alarm.manager.domain.slack;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlockType {
    HEADER("header"),
    DIVIDER("divider"),
    SECTION("section"),
    MARKDOWN("mrkdwn"),
    PLAIN_TEXT("plain_text");

    private String value;
}
