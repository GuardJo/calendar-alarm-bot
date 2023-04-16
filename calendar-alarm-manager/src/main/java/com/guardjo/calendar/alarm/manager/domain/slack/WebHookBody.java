package com.guardjo.calendar.alarm.manager.domain.slack;

import java.util.List;

public record WebHookBody(List<SlackBlock> blocks) {
    public static WebHookBody of(List<SlackBlock> blocks) {
        return new WebHookBody(blocks);
    }
}
