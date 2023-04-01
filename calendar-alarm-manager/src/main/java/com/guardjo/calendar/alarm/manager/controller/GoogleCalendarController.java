package com.guardjo.calendar.alarm.manager.controller;

import com.guardjo.calendar.alarm.manager.constant.UrlConstant;
import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarEventResponse;
import com.guardjo.calendar.alarm.manager.service.GoogleApiConnectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(UrlConstant.GOOGLE_CALENDAR_PREFIX)
@Slf4j
public class GoogleCalendarController {
    private final GoogleApiConnectService googleApiConnectService;

    public GoogleCalendarController(@Autowired GoogleApiConnectService googleApiConnectService) {
        this.googleApiConnectService = googleApiConnectService;
    }

    @GetMapping(UrlConstant.GET_CALENDAR_EVENT_LIST_URL)
    public GoogleCalendarEventResponse findGoogleCalendarEvents(@RequestParam String calendarId) {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusDays(1L);
        log.info("[Test] Request Find GoogleCalendarEvents, calendarId = {}, start = {}, end = {}", calendarId, startTime, endTime);

        return googleApiConnectService.searchEvents(calendarId, startTime, endTime);
    }
}
