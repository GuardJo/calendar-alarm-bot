package com.guardjo.calendar.alarm.manager.scheduler;

import com.guardjo.calendar.alarm.manager.domain.AlarmSettingDto;
import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarEventResponse;
import com.guardjo.calendar.alarm.manager.service.AlarmSettingService;
import com.guardjo.calendar.alarm.manager.service.GoogleApiConnectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CalendarEventTransfer {
    private final GoogleApiConnectService googleApiConnectService;
    private final AlarmSettingService alarmSettingService;

    public CalendarEventTransfer(GoogleApiConnectService googleApiConnectService,
                                 AlarmSettingService alarmSettingService) {
        this.googleApiConnectService = googleApiConnectService;
        this.alarmSettingService = alarmSettingService;
    }

    // TODO 테스트 용도로 10초 마다 구동되게끔 설정 (향후 변경 예정)
    @Scheduled(cron = "*/10 * * * * *")
    public List<GoogleCalendarEventResponse> readAllGoogleCalendarEvents() {
        log.info("[Test] Scheduling Start, FindAll GoogleCalendarEvents");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusDays(1L);
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
        return googleCalendarEventResponses;
    }

    private GoogleCalendarEventResponse readGoogleCalendarEvents(String calendarId, String accessToken, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            return googleApiConnectService.searchEvents(calendarId, accessToken, startTime, endTime);
        } catch (Exception e) {
            log.warn("[Test] {}", e.getMessage());
            return null;
        }
    }
}
