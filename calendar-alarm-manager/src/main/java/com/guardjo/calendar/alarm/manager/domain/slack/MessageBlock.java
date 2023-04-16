package com.guardjo.calendar.alarm.manager.domain.slack;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class MessageBlock implements SlackBlock {
    private String type;
    private MessageText text;
}
