package com.guardjo.calendar.alarm.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class GoogleAccountInfo {
    private String id;
    private String email;
    private String displayName;
    private boolean isSelf;

    protected GoogleAccountInfo() {

    }
}
