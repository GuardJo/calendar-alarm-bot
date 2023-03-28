package com.guardjo.calendar.alarm.manager.controller;

import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarEventResponse;
import com.guardjo.calendar.alarm.manager.service.GoogleApiConnectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/google-calendar")
@Slf4j
public class GoogleCalendarController {
    private final GoogleApiConnectService googleApiConnectService;

    public GoogleCalendarController(@Autowired GoogleApiConnectService googleApiConnectService) {
        this.googleApiConnectService = googleApiConnectService;
    }

    @GetMapping("/events")
    public GoogleCalendarEventResponse findGoogleCalendarEvents(@RequestParam String calendarId) {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusDays(1L);
        log.info("[Test] Request Find GoogleCalendarEvents, calendarId = {}, start = {}, end = {}", calendarId, startTime, endTime);

        return googleApiConnectService.searchEvents(calendarId, startTime, endTime);
    }
}
