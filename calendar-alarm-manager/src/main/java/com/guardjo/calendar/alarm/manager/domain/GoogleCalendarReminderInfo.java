package com.guardjo.calendar.alarm.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class GoogleCalendarReminderInfo {
    private String method;
    private int minutes;

    protected GoogleCalendarReminderInfo() {

    }
}
