package com.guardjo.calendar.alarm.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchStopRequest {
    private String id;
    private String resourceId;
}
