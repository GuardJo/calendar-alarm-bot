package com.guardjo.calendar.alarm.manager.service;

import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarEventResponse;
import com.guardjo.calendar.alarm.manager.domain.slack.BlockType;
import com.guardjo.calendar.alarm.manager.domain.slack.MessageBlock;
import com.guardjo.calendar.alarm.manager.domain.slack.MessageText;
import com.guardjo.calendar.alarm.manager.domain.slack.WebHookBody;
import com.guardjo.calendar.alarm.manager.util.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class WebhookConnectServiceTest {
    @Mock
    private WebClient slackClient;
    @Mock
    private SlackBlockGenerator slackBlockGenerator;
    @InjectMocks
    private WebhookConnectService webhookConnectService;

    @DisplayName("슬랙 웹훅으로 데이터 송신 테스트")
    @Test
    void testSendData() {
        GoogleCalendarEventResponse testResponse = TestDataGenerator.generateGoogleCalendarEventResponse();
        WebHookBody testBody = WebHookBody.of(
                List.of(MessageBlock.of(
                        BlockType.HEADER.getValue(), MessageText.of(
                                BlockType.PLAIN_TEXT.getValue(), "test"))));

        WebClient.RequestBodyUriSpec requestBodyUriSpec = Mockito.mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = Mockito.mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);
        Mono<String> mono = Mockito.mock(Mono.class);

        // BodyInserters.fromValue(SlackWebHookBody.of(googleCalendarEventResponse.getSummary()))

        given(slackBlockGenerator.generateBlock(testResponse)).willReturn(testBody);
        given(slackClient.post()).willReturn(requestBodyUriSpec);
        given(requestBodyUriSpec.contentType(MediaType.APPLICATION_JSON)).willReturn(requestBodySpec);
        given(requestBodySpec.body(any(Mono.class), eq(WebHookBody.class))).willReturn(requestHeadersSpec);
        given(requestHeadersSpec.retrieve()).willReturn(responseSpec);
        given(responseSpec.bodyToMono(eq(String.class))).willReturn(mono);
        given(mono.block()).willReturn("ok");

        assertThatCode(() -> webhookConnectService.sendEvents(testResponse)).doesNotThrowAnyException();
    }
}