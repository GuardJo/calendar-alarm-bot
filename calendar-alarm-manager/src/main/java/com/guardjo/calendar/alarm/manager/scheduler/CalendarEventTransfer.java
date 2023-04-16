package com.guardjo.calendar.alarm.manager.scheduler;

import com.guardjo.calendar.alarm.manager.domain.AlarmSettingDto;
import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarEventResponse;
import com.guardjo.calendar.alarm.manager.service.AlarmSettingService;
import com.guardjo.calendar.alarm.manager.service.GoogleApiConnectService;
import com.guardjo.calendar.alarm.manager.service.WebhookConnectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CalendarEventTransfer {
    private final GoogleApiConnectService googleApiConnectService;
    private final AlarmSettingService alarmSettingService;
    private final WebhookConnectService webhookConnectService;

    public CalendarEventTransfer(GoogleApiConnectService googleApiConnectService,
                                 AlarmSettingService alarmSettingService,
                                 WebhookConnectService webhookConnectService) {
        this.googleApiConnectService = googleApiConnectService;
        this.alarmSettingService = alarmSettingService;
        this.webhookConnectService = webhookConnectService;
    }

    // TODO 테스트 용도로 10초 마다 구동되게끔 설정 (향후 변경 예정)
    @Scheduled(cron = "*/10 * * * * *")
    public List<GoogleCalendarEventResponse> readAllGoogleCalendarEvents() {
        log.info("[Test] Scheduling Start, FindAll GoogleCalendarEvents");
        ZonedDateTime startTime = ZonedDateTime.now();
        ZonedDateTime endTime = ZonedDateTime.now().plusDays(1L);
        List<AlarmSettingDto> alarmSettingDtos = alarmSettingService.findAll().stream()
                .map(AlarmSettingDto::from)
                .collect(Collectors.toList());
        List<GoogleCalendarEventResponse> googleCalendarEventResponses = new ArrayList<>();

        if (alarmSettingDtos.isEmpty()) {
            log.warn("[Test] Empty AlarmSettings");
            return null;
        }

        alarmSettingDtos.forEach((alarmSettingDto -> {
            GoogleCalendarEventResponse response = readGoogleCalendarEvents(
                    alarmSettingDto.calendarId(),
                    alarmSettingDto.accessToken(),
                    startTime,
                    endTime
            );

            if (response != null) {
                googleCalendarEventResponses.add(response);
            }
        }));

        log.info("[Test] response = {}", googleCalendarEventResponses.get(0).toString());
        // TODO 추후 슬랙 메시지로 전달
        googleCalendarEventResponses.stream()
                .forEach(response -> webhookConnectService.sendEvents(response));

        return googleCalendarEventResponses;
    }

    private GoogleCalendarEventResponse readGoogleCalendarEvents(String calendarId, String accessToken, ZonedDateTime startTime, ZonedDateTime endTime) {
        try {
            return googleApiConnectService.searchEvents(calendarId, accessToken, startTime, endTime);
        } catch (Exception e) {
            log.warn("[Test] {}", e.getMessage());
            return null;
        }
    }
}
