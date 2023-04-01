package com.guardjo.calendar.alarm.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
public class GoogleCalendarEventDto {
    private String kind = "calendar#event";
    private String etag;
    private String id;
    private String status;
    private String htmlLink;
    private ZonedDateTime created;
    private ZonedDateTime updated;
    private String summary;
    private String description;
    private String location;
    private String colorId;
    private GoogleAccountInfo creator;
    private GoogleAccountInfo organizer;
    private GoogleCalendarScheduleInfo start;
    private GoogleCalendarScheduleInfo end;
    private boolean isEndTimeUnspecified;
    private List<String> recurrence = new ArrayList<>();
    private String recurringEventId;
    private GoogleCalendarScheduleInfo originalStartTime;

    protected GoogleCalendarEventDto() {

    }
}
