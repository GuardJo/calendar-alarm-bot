package com.guardjo.calendar.alarm.manager.domain.slack;

import java.util.List;

public record WebHookBody(List<MessageBlock> blocks) {
    public static WebHookBody of(List<MessageBlock> blocks) {
        return new WebHookBody(blocks);
    }
}
