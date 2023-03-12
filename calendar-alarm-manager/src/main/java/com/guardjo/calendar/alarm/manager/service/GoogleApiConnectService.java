package com.guardjo.calendar.alarm.manager.service;

import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarDto;
import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarSettingDto;
import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarSettingsDto;
import com.guardjo.calendar.alarm.manager.util.AccessTokenGenerator;
import com.guardjo.calendar.alarm.manager.util.GoogleCalendarAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Slf4j
public class GoogleApiConnectService {
    private final WebClient webClient;
    private final AccessTokenGenerator accessTokenGenerator;

    public GoogleApiConnectService(@Autowired WebClient webClient, @Autowired AccessTokenGenerator accessTokenGenerator) {
        this.webClient = webClient;
        this.accessTokenGenerator = accessTokenGenerator;
    }

    public GoogleCalendarDto findCalendar(String calendarId) {
        log.info("[Test] Find Calendar");
        GoogleCalendarDto response = webClient.get()
                .uri( GoogleCalendarAPI.REQUEST_GET_CALENDAR_URL + "/" + calendarId)
                .header(HttpHeaders.AUTHORIZATION, accessTokenGenerator.getAccessToken())
                .retrieve()
                .bodyToMono(GoogleCalendarDto.class)
                .block();

        return response;
    }

    public GoogleCalendarSettingsDto getCalendarSettings() {
        log.info("[Test] Find Calendar Settings");
        GoogleCalendarSettingsDto googleCalendarSettingsDto = webClient.get()
                .uri(GoogleCalendarAPI.REQUEST_GET_CALENDAR_SETTINGS)
                .header(HttpHeaders.AUTHORIZATION, accessTokenGenerator.getAccessToken())
                .retrieve()
                .bodyToMono(GoogleCalendarSettingsDto.class)
                .block();

        log.info("[Test] Response : {}", googleCalendarSettingsDto.toString());

        return googleCalendarSettingsDto;
    }
}
