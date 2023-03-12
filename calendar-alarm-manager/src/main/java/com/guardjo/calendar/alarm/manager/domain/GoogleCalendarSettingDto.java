package com.guardjo.calendar.alarm.manager.domain;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GoogleCalendarSettingDto {
    private String kind;
    private String etag;
    private String id;
    private String value;
}
