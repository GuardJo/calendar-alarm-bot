package com.guardjo.calendar.alarm.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor(staticName = "of")
@ToString
public class GoogleCalendarScheduleInfo {
    private Date date;
    private ZonedDateTime dateTime;
    private String timeZone;

    protected GoogleCalendarScheduleInfo() {

    }
}
