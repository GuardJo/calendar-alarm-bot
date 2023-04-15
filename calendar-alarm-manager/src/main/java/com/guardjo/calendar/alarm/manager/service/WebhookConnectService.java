package com.guardjo.calendar.alarm.manager.service;

import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarEventResponse;
import com.guardjo.calendar.alarm.manager.domain.slack.SlackWebHookBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class WebhookConnectService {
    private final WebClient slackClient;

    public WebhookConnectService(WebClient slackClient) {
        this.slackClient = slackClient;
    }

    public void sendEvents(GoogleCalendarEventResponse googleCalendarEventResponse) {
        slackClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(SlackWebHookBody.of(googleCalendarEventResponse.getSummary())))
                .retrieve();

        log.info("[Test] GoogleEvents summary = {}", googleCalendarEventResponse.getSummary());
    }
}
