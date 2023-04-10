package com.guardjo.calendar.alarm.manager.domain;

import lombok.AllArgsConstructor;

public record AlarmSettingDto(
        String calendarId,
        String accessToken
) {
    public static AlarmSettingDto from(AlarmSetting alarmSetting) {
        return new AlarmSettingDto(
                alarmSetting.getCalendarId(),
                alarmSetting.getAccessToken()
        );
    }

    public static AlarmSetting toEntity(AlarmSettingDto alarmSettingDto) {
        return AlarmSetting.of(
                null,
                alarmSettingDto.calendarId,
                alarmSettingDto.accessToken
        );
    }
}
