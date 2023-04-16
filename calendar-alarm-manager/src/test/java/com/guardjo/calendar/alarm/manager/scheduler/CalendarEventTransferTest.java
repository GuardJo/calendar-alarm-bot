package com.guardjo.calendar.alarm.manager.scheduler;

import com.guardjo.calendar.alarm.manager.domain.AlarmSetting;
import com.guardjo.calendar.alarm.manager.domain.AlarmSettingDto;
import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarEventResponse;
import com.guardjo.calendar.alarm.manager.service.AlarmSettingService;
import com.guardjo.calendar.alarm.manager.service.GoogleApiConnectService;
import com.guardjo.calendar.alarm.manager.service.WebhookConnectService;
import com.guardjo.calendar.alarm.manager.util.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CalendarEventTransferTest {
    @Mock
    private GoogleApiConnectService googleApiConnectService;
    @Mock
    private AlarmSettingService alarmSettingService;
    @Mock
    private WebhookConnectService webhookConnectService;
    @InjectMocks
    private CalendarEventTransfer calendarEventTransfer;

    @DisplayName("금일의 구글 캘린더 이벤트 목록 반환 테스트")
    @Test
    void testReadGoogleCalendarEvents() {
        String calendarId = "testId";
        String accessToken = "testToken";
        GoogleCalendarEventResponse expectedResponse = TestDataGenerator.generateGoogleCalendarEventResponse();
        List<AlarmSetting> alarmSettings = List.of(AlarmSetting.of(0L, calendarId, accessToken));

        given(alarmSettingService.findAll()).willReturn(alarmSettings);
        given(googleApiConnectService.searchEvents(anyString(), anyString(), any(ZonedDateTime.class), any(ZonedDateTime.class)))
                .willReturn(expectedResponse);
        willDoNothing().given(webhookConnectService).sendEvents(expectedResponse);

        List<GoogleCalendarEventResponse> actualResponse = calendarEventTransfer.readAllGoogleCalendarEvents();

        assertThat(actualResponse).isEqualTo(List.of(expectedResponse));

        then(alarmSettingService).should().findAll();
        then(googleApiConnectService).should().searchEvents(anyString(), anyString(), any(ZonedDateTime.class), any(ZonedDateTime.class));
        then(webhookConnectService).should().sendEvents(expectedResponse);
    }
}