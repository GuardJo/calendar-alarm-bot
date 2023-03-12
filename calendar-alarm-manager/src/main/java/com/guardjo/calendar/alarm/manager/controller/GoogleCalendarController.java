package com.guardjo.calendar.alarm.manager.controller;

import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarDto;
import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarSettingsDto;
import com.guardjo.calendar.alarm.manager.service.GoogleApiConnectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/google-calendar")
@Slf4j
public class GoogleCalendarController {
    private final GoogleApiConnectService googleApiConnectService;

    public GoogleCalendarController(@Autowired GoogleApiConnectService googleApiConnectService) {
        this.googleApiConnectService = googleApiConnectService;
    }

    @GetMapping("/detail")
    public GoogleCalendarDto findCalendar(@RequestParam String calendarId) {
        log.info("[Test] Request Find Google Calendar, calendarId = {}", calendarId);
        return googleApiConnectService.findCalendar(calendarId);
    }

    @GetMapping("/settings")
    public GoogleCalendarSettingsDto findCalendarSetting(@AuthenticationPrincipal OAuth2User oAuth2User) {
        log.info("[Test] Request Find Google Calendar Setting");
        log.info("user = {}", oAuth2User);
        return googleApiConnectService.getCalendarSettings();
    }

    @PostMapping("/watch")
    public String requestWatchEvent() {
        //TODO watch 이벤트 요청 전송
        log.info("[Test] Request Google Calendar Event Watch");
        return "redirect:/";
    }
}
