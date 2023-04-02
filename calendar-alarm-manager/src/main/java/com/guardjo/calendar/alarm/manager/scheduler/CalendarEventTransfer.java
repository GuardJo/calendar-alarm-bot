package com.guardjo.calendar.alarm.manager.scheduler;

import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarEventResponse;
import com.guardjo.calendar.alarm.manager.service.GoogleApiConnectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class CalendarEventTransfer {
    private final GoogleApiConnectService googleApiConnectService;

    public CalendarEventTransfer(GoogleApiConnectService googleApiConnectService) {
        this.googleApiConnectService = googleApiConnectService;
    }

    // TODO 테스트 용도로 10초 마다 구동되게끔 설정 (향후 변경 예정)
    @Scheduled(cron = "*/10 * * * * *")
    public GoogleCalendarEventResponse readGoogleCalendarEvents() {
        log.info("[Test] Scheduling Start, FindAll GoogleCalendarEvents");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusDays(1L);
        String calendarId = "dagh1218@gmail.com";

        try {
            return googleApiConnectService.searchEvents(calendarId, startTime, endTime);
        } catch (Exception e) {
            log.warn("[Test] {}", e.getMessage());
            return null;
        }
    }
}
