package com.guardjo.calendar.alarm.manager.scheduler;

import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarEventResponse;
import com.guardjo.calendar.alarm.manager.service.GoogleApiConnectService;
import com.guardjo.calendar.alarm.manager.util.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class CalendarEventTransferTest {
    @Mock
    private GoogleApiConnectService googleApiConnectService;

    @InjectMocks
    private CalendarEventTransfer calendarEventTransfer;

    @DisplayName("금일의 구글 캘린더 이벤트 목록 반환 테스트")
    @Test
    void testReadGoogleCalendarEvents() {
        GoogleCalendarEventResponse expectedResponse = TestDataGenerator.generateGoogleCalendarEventResponse();

        given(googleApiConnectService.searchEvents(anyString(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(expectedResponse);

        List<GoogleCalendarEventResponse> actualResponse = calendarEventTransfer.readAllGoogleCalendarEvents();

        assertThat(actualResponse).isEqualTo(List.of(expectedResponse));

        then(googleApiConnectService).should().searchEvents(anyString(), any(LocalDateTime.class), any(LocalDateTime.class));
    }
}