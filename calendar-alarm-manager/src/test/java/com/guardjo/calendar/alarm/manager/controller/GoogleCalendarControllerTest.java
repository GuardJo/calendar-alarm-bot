package com.guardjo.calendar.alarm.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guardjo.calendar.alarm.manager.config.SecurityConfig;
import com.guardjo.calendar.alarm.manager.domain.*;
import com.guardjo.calendar.alarm.manager.service.GoogleApiConnectService;
import com.guardjo.calendar.alarm.manager.util.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(GoogleCalendarController.class)
class GoogleCalendarControllerTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    private final static String GOOGLE_CALENDAR_PREFIX = "/google-calendar";
    private final static String GET_CALENDAR_URL = GOOGLE_CALENDAR_PREFIX + "/detail";
    private final static String GET_CALENDAR_SETTINGS_URL = GOOGLE_CALENDAR_PREFIX + "/settings";
    private final static String POST_CALEDAR_WATCH_URL = GOOGLE_CALENDAR_PREFIX + "/watch";

    @MockBean
    private GoogleApiConnectService googleApiConnectService;

    GoogleCalendarControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @DisplayName("구글 캘린더 데이터 반환 테스트")
    @Test
    @WithMockUser
    void testGetCalendar() throws Exception {
        String testCalendarId = "test@gmail.com";
        GoogleCalendarDto googleCalendarDto = TestDataGenerator.generateGoogleCalendarDto();

        given(googleApiConnectService.findCalendar(anyString())).willReturn(googleCalendarDto);

        mockMvc.perform(get(GET_CALENDAR_URL)
                        .queryParam("calendarId", testCalendarId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString().equals(objectMapper.writeValueAsString(googleCalendarDto));
    }

    @DisplayName("구글 캘린더 설정 데이터 반환 테스트")
    @Test
    @WithMockUser
    void testGetSettings() throws Exception {
        GoogleCalendarSettingsDto googleCalendarSettingsDto = TestDataGenerator.generateGoogleCalendarSettingsDto();

        given(googleApiConnectService.getCalendarSettings()).willReturn(googleCalendarSettingsDto);

        mockMvc.perform(get(GET_CALENDAR_SETTINGS_URL))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString().equals(objectMapper.writeValueAsString(googleCalendarSettingsDto));
    }

    @DisplayName("구글 캘린더 watch 요청 전달 테스트")
    @Test
    @WithMockUser
    void testPostWatch() throws Exception {
        WatchRequest watchRequest = TestDataGenerator.generateWatchRequest();
        WatchResponse watchResponse = TestDataGenerator.generateWatchResponse();

        mockMvc.perform(post(POST_CALEDAR_WATCH_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(watchRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString().equals(objectMapper.writeValueAsString(watchResponse));
    }
}