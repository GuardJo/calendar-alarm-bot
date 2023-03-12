package com.guardjo.calendar.alarm.manager.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
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
