package com.guardjo.calendar.alarm.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor(staticName = "of")
public class AlarmSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String calendarId;

    @Setter
    private String accessToken;

    protected AlarmSetting() {

    }
}
