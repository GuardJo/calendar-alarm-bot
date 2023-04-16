package com.guardjo.calendar.alarm.manager.service;

import com.guardjo.calendar.alarm.manager.config.JacksonConfig;
import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarEventResponse;
import com.guardjo.calendar.alarm.manager.domain.exception.EventNotFoundException;
import com.guardjo.calendar.alarm.manager.util.AccessTokenGenerator;
import com.guardjo.calendar.alarm.manager.util.GoogleCalendarAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class GoogleApiConnectService {
    private final WebClient webClient;
    public GoogleApiConnectService(@Autowired WebClient webClient) {
        this.webClient = webClient;
    }

    public GoogleCalendarEventResponse searchEvents(String calendarId, String accessToken, ZonedDateTime start, ZonedDateTime end) {
        String url = generateSearchCalendarEventsUrl(calendarId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(JacksonConfig.DATE_TIME_FORMAT);
        
        // Google Calendar API에서 시간값으로 탐색 시 UTC 기반으로 탐색하기 떄문에 UTC로 변환
        String timeMin = start.format(formatter.withZone(ZoneId.of("UTC")));
        String timeMax = end.format(formatter.withZone(ZoneId.of("UTC")));

        log.info("[Test] Search Google Calendar Events, calendarId = {}, startTime = {}, endTime = {}", calendarId, timeMin, timeMax);

        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path(url)
                                .queryParam("timeMin", timeMin)
                                .queryParam("timeMax", timeMax)
                                .build()
                )
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new EventNotFoundException(calendarId));
                    }
                    else {
                        return Mono.error(new RuntimeException("Client Error : " + clientResponse.statusCode()));
                    }
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    return Mono.error(new RuntimeException("Server Error : " + clientResponse.statusCode()));
                })
                .bodyToMono(GoogleCalendarEventResponse.class)
                .block();
    }

    /*
    google calendar event list 호출 api에 calendarId 삽입
     */
    private String generateSearchCalendarEventsUrl(String calendarId) {
        String postfixUrl = GoogleCalendarAPI.REQUEST_CALENDAR_EVENTS_URL;
        postfixUrl = postfixUrl.replace("{calendarId}", calendarId);

        return postfixUrl;
    }
}
