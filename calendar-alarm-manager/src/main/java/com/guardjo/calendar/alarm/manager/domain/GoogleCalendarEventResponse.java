package com.guardjo.calendar.alarm.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
public class GoogleCalendarEventResponse {
    private String kind;
    private String etag;
    private String summary;
    private String description;
    private ZonedDateTime updated;
    private String timeZone;
    private String accessRole;
    private List<GoogleCalendarReminderInfo> defaultReminders = new ArrayList<>();
    private String nextPageToken;
    private String nextSyncToken;
    private List<GoogleCalendarEventDto> items = new ArrayList<>();

    protected GoogleCalendarEventResponse() {

    }
}
