package com.guardjo.calendar.alarm.manager.service;

import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarEventResponse;
import com.guardjo.calendar.alarm.manager.domain.slack.WebHookBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class WebhookConnectService {
    private final WebClient slackClient;
    private final SlackBlockGenerator slackBlockGenerator;

    public WebhookConnectService(WebClient slackClient, SlackBlockGenerator slackBlockGenerator) {
        this.slackClient = slackClient;
        this.slackBlockGenerator = slackBlockGenerator;
    }

    public void sendEvents(GoogleCalendarEventResponse googleCalendarEventResponse) {
        WebHookBody webHookBody = slackBlockGenerator.generateBlock(googleCalendarEventResponse);

        String result = slackClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(webHookBody), WebHookBody.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("[Test] GoogleEvents summary = {}, sendResult = {}", googleCalendarEventResponse.getSummary(), result);
    }
}
