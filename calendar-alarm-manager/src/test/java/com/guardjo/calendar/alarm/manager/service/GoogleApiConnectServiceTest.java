package com.guardjo.calendar.alarm.manager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarEventResponse;
import com.guardjo.calendar.alarm.manager.util.AccessTokenGenerator;
import com.guardjo.calendar.alarm.manager.util.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GoogleApiConnectServiceTest {
    @Mock
    private WebClient webClient;
    @Mock
    private AccessTokenGenerator accessTokenGenerator;

    @InjectMocks
    private GoogleApiConnectService googleApiConnectService;

    @DisplayName("구글 캘린더 이벤트 목록 조회 요청 및 반환 테스트")
    @Test
    void testSearchEvents() {
        String calendarId = "testId";
        String accessToken = "testToken";
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(1L);

        GoogleCalendarEventResponse expectedResponse = TestDataGenerator.generateGoogleCalendarEventResponse();

        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        given(webClient.get()).willReturn(requestHeadersUriSpec);
        given(requestHeadersUriSpec.uri(any(Function.class))).willReturn(requestHeadersUriSpec);
        given(requestHeadersUriSpec.header(eq(HttpHeaders.AUTHORIZATION), anyString())).willReturn(requestHeadersUriSpec);
        given(requestHeadersUriSpec.retrieve()).willReturn(responseSpec);
        given(responseSpec.onStatus(any(Predicate.class), any(Function.class))).willReturn(responseSpec);
        given(responseSpec.bodyToMono(GoogleCalendarEventResponse.class)).willReturn(Mono.just(expectedResponse));

        GoogleCalendarEventResponse actualResponse = googleApiConnectService.searchEvents(calendarId, accessToken, start, end);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}