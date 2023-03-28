package com.guardjo.calendar.alarm.manager.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor(staticName = "of")
public class GoogleCalendarScheduleInfo {
    private Date date;
    private ZonedDateTime dateTime;
    private String timeZone;

    protected GoogleCalendarScheduleInfo() {

    }
}
