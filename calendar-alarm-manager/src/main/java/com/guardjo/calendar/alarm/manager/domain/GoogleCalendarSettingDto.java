package com.guardjo.calendar.alarm.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GoogleCalendarSettingDto {
    private String kind;
    private String etag;
    private String id;
    private String value;
}
