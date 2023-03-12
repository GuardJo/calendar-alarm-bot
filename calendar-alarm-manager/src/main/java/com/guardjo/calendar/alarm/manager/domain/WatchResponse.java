package com.guardjo.calendar.alarm.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchResponse {
    private String kind;
    private String id;
    private String resourceId;
    private String resourceUri;
    private String token;
    private long expiration;
}
