package com.guardjo.calendar.alarm.manager.controller;

import com.guardjo.calendar.alarm.manager.constant.UrlConstant;
import com.guardjo.calendar.alarm.manager.domain.AlarmSettingDto;
import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarEventResponse;
import com.guardjo.calendar.alarm.manager.service.AlarmSettingService;
import com.guardjo.calendar.alarm.manager.service.GoogleApiConnectService;
import com.guardjo.calendar.alarm.manager.util.AccessTokenGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@RestController
@RequestMapping(UrlConstant.GOOGLE_CALENDAR_PREFIX)
@Slf4j
public class GoogleCalendarController {
    private final GoogleApiConnectService googleApiConnectService;
    private final AlarmSettingService alarmSettingService;
    private final AccessTokenGenerator accessTokenGenerator;

    public GoogleCalendarController(GoogleApiConnectService googleApiConnectService,
                                    AlarmSettingService alarmSettingService,
                                    AccessTokenGenerator accessTokenGenerator) {
        this.googleApiConnectService = googleApiConnectService;
        this.alarmSettingService = alarmSettingService;
        this.accessTokenGenerator = accessTokenGenerator;
    }

    @GetMapping(UrlConstant.GET_CALENDAR_EVENT_LIST_URL)
    public GoogleCalendarEventResponse findGoogleCalendarEvents(@RequestParam String calendarId) {
        ZonedDateTime startTime = ZonedDateTime.now();
        ZonedDateTime endTime = ZonedDateTime.now().plusDays(1L);
        log.info("[Test] Request Find GoogleCalendarEvents, calendarId = {}, start = {}, end = {}", calendarId, startTime, endTime);

        return googleApiConnectService.searchEvents(calendarId, accessTokenGenerator.getAccessToken(), startTime, endTime);
    }

    @PostMapping(UrlConstant.SAVE_ALARM_SETTING_URL)
    public void saveGoogleCalendarAlarmSetting(@RequestParam String calendarId) {
        log.info("[Test] Request Save AlarmSetting, calendarId = {}", calendarId);

        AlarmSettingDto alarmSettingDto = new AlarmSettingDto(calendarId, accessTokenGenerator.getAccessToken());

        alarmSettingService.saveAlarmSetting(alarmSettingDto);
    }
}
