package com.guardjo.calendar.alarm.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.guardjo.calendar.alarm.manager.config.TestConfig;
import com.guardjo.calendar.alarm.manager.constant.UrlConstant;
import com.guardjo.calendar.alarm.manager.domain.AlarmSettingDto;
import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarEventResponse;
import com.guardjo.calendar.alarm.manager.domain.exception.EventNotFoundException;
import com.guardjo.calendar.alarm.manager.service.AlarmSettingService;
import com.guardjo.calendar.alarm.manager.service.GoogleApiConnectService;
import com.guardjo.calendar.alarm.manager.util.AccessTokenGenerator;
import com.guardjo.calendar.alarm.manager.util.TestDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestConfig.class)
@WebMvcTest(GoogleCalendarController.class)
class GoogleCalendarControllerTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final static String TEST_GET_CALENDAR_EVENT_LIST =
            UrlConstant.GOOGLE_CALENDAR_PREFIX + UrlConstant.GET_CALENDAR_EVENT_LIST_URL;
    private final static String TEST_SAVE_CALENDAR_ALARM_SETTING =
            UrlConstant.GOOGLE_CALENDAR_PREFIX + UrlConstant.SAVE_ALARM_SETTING_URL;

    @MockBean
    private GoogleApiConnectService googleApiConnectService;
    @MockBean
    private AlarmSettingService alarmSettingService;
    @MockBean
    private AccessTokenGenerator accessTokenGenerator;

    GoogleCalendarControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_WITH_CONTEXT_TIME_ZONE, false);
    }

    @DisplayName("구글 캘린더 이벤트 조회 요청 테스트")
    @WithMockUser
    @Test
    void testGetCalendarEvents() throws Exception {
        GoogleCalendarEventResponse googleCalendarEventResponse = TestDataGenerator.generateGoogleCalendarEventResponse();
        String expectedResponse = objectMapper.writeValueAsString(googleCalendarEventResponse);
        String accessToken = "testToken";

        given(accessTokenGenerator.getAccessToken()).willReturn(accessToken);
        given(googleApiConnectService.searchEvents(anyString(), eq(accessToken), any(ZonedDateTime.class), any(ZonedDateTime.class)))
                .willReturn(googleCalendarEventResponse);

        String actualResponse = mockMvc.perform(get(TEST_GET_CALENDAR_EVENT_LIST)
                        .queryParam("calendarId", "test"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(actualResponse).isEqualTo(expectedResponse);
        then(googleApiConnectService).should().searchEvents(anyString(), eq(accessToken), any(ZonedDateTime.class), any(ZonedDateTime.class));
    }

    @DisplayName("구글 로그인 정보 없이 특정 캘린더 이벤트 목록 요청 테스트")
    @Test
    void testGetCalendarWithoutAuthentication() throws Exception {
        GoogleCalendarEventResponse googleCalendarEventResponse = TestDataGenerator.generateGoogleCalendarEventResponse();

        given(googleApiConnectService.searchEvents(anyString(), anyString(), any(ZonedDateTime.class), any(ZonedDateTime.class)))
                .willReturn(googleCalendarEventResponse);

        mockMvc.perform(get(TEST_GET_CALENDAR_EVENT_LIST)
                        .queryParam("calendarId", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));

        then(googleApiConnectService).shouldHaveNoInteractions();
    }

    @DisplayName("올바르지 않은 calendarId를 통해 구글 캘린더 이벤트 목록 조회 테스트")
    @WithMockUser
    @Test
    void testGetCalendarEventListWithWrongCalendarId() throws Exception {
        String accessToken = "testToken";
        given(accessTokenGenerator.getAccessToken()).willReturn(accessToken);
        given(googleApiConnectService.searchEvents(anyString(), eq(accessToken), any(ZonedDateTime.class), any(ZonedDateTime.class)))
                .willThrow(new EventNotFoundException("wrongId"));

        mockMvc.perform(get(TEST_GET_CALENDAR_EVENT_LIST)
                        .queryParam("calendarId", "wrongId")
                )
                .andExpect(status().isNotFound());

        then(googleApiConnectService).should().searchEvents(anyString(), eq(accessToken), any(ZonedDateTime.class), any(ZonedDateTime.class));
    }

    @DisplayName("구글 캘린더 알림 설정 저장 테스트")
    @WithMockUser
    @Test
    void testSaveAlarmSetting() throws Exception {
        willDoNothing().given(alarmSettingService).saveAlarmSetting(any(AlarmSettingDto.class));
        given(accessTokenGenerator.getAccessToken()).willReturn("testToken");

        mockMvc.perform(post(TEST_SAVE_CALENDAR_ALARM_SETTING)
                        .queryParam("calendarId", "testId")
                )
                .andExpect(status().isOk());

        then(alarmSettingService).should().saveAlarmSetting(any(AlarmSettingDto.class));
        then(accessTokenGenerator).should().getAccessToken();
    }

    @DisplayName("구글 로그인 정보 없이 알림 설정 저장 요청 테스트")
    @Test
    void testSaveAlarmSettingWithoutLogin() throws Exception {
        willDoNothing().given(alarmSettingService).saveAlarmSetting(any(AlarmSettingDto.class));
        given(accessTokenGenerator.getAccessToken()).willReturn("testToken");

        mockMvc.perform(post(TEST_SAVE_CALENDAR_ALARM_SETTING)
                        .queryParam("calendarId", "testId")
                )
                .andExpect(status().is3xxRedirection());

        then(alarmSettingService).shouldHaveNoInteractions();
        then(accessTokenGenerator).shouldHaveNoInteractions();
    }
}