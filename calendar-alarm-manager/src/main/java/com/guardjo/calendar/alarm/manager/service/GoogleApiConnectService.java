package com.guardjo.calendar.alarm.manager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guardjo.calendar.alarm.manager.domain.*;
import com.guardjo.calendar.alarm.manager.util.AccessTokenGenerator;
import com.guardjo.calendar.alarm.manager.util.GoogleCalendarAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class GoogleApiConnectService {
    private final WebClient webClient;
    private final AccessTokenGenerator accessTokenGenerator;
    private final ObjectMapper objectMapper;

    public GoogleApiConnectService(@Autowired WebClient webClient, @Autowired AccessTokenGenerator accessTokenGenerator) {
        this.webClient = webClient;
        this.accessTokenGenerator = accessTokenGenerator;
        this.objectMapper = new ObjectMapper();
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

    public WatchResponse updateWatchEvent(String calendarId, WatchRequest watchRequest) throws JsonProcessingException {
        log.info("[Test] Update Calendar Watch, request : {}", watchRequest.toString());
        String requestBody = objectMapper.writeValueAsString(watchRequest);

        WatchResponse response = webClient.post()
                .uri(GoogleCalendarAPI.REQUEST_WATCH_EVENT_URL.replace("{calendarId}", calendarId))
                .header(HttpHeaders.AUTHORIZATION, accessTokenGenerator.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(WatchResponse.class)
                .block();

        log.info("[Test] Response : {}", response.toString());

        return response;
    }

    public void stopWatchEvent(WatchStopRequest watchStopRequest) throws JsonProcessingException {
        log.info("[Test] Stop Calendar Watch, id = {}, resourceId = {}", watchStopRequest.getId(), watchStopRequest.getResourceId());
        String requestBody = objectMapper.writeValueAsString(watchStopRequest);

        webClient.post()
                .uri(GoogleCalendarAPI.REQUEST_WATCH_STOP_URL)
                .header(HttpHeaders.AUTHORIZATION, accessTokenGenerator.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve();
    }
}
