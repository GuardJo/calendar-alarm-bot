package com.guardjo.calendar.alarm.manager.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GoogleCalendarDto {
    private String kind;
    private String etag;
    private String id;
    private String summary;
    private String description;
    private String location;
    private String timeZone;
    private ConferenceProperties conferenceProperties;
}