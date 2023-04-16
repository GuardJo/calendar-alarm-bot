package com.guardjo.calendar.alarm.manager.domain.slack;

import lombok.Getter;

@Getter
public class MessageDivider implements SlackBlock {
    private String type = BlockType.DIVIDER.getValue();

    public static MessageDivider of() {
        return new MessageDivider();
    }
}
