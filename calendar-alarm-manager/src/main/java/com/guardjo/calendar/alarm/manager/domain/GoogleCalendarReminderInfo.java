package com.guardjo.calendar.alarm.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor(staticName = "of")
@ToString
public class GoogleCalendarReminderInfo {
    private String method;
    private int minutes;

    protected GoogleCalendarReminderInfo() {

    }
}
