package com.guardjo.calendar.alarm.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GoogleCalendarSettingsDto {
    private String kind;
    private String etag;
    private String nextPageToken;
    private String nextSyncToken;
    private List<GoogleCalendarSettingDto> items = new ArrayList<>();
}
