package com.guardjo.calendar.alarm.manager.service;

import com.guardjo.calendar.alarm.manager.domain.AlarmSetting;
import com.guardjo.calendar.alarm.manager.domain.AlarmSettingDto;
import com.guardjo.calendar.alarm.manager.repository.AlarmSettingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AlarmSettingServiceTest {
    @Mock
    private AlarmSettingRepository alarmSettingRepository;
    @InjectMocks
    private AlarmSettingService alarmSettingService;

    @DisplayName("신규 알람 설정 저장 테스트")
    @Test
    void testSaveNewAlarmSetting() {
        AlarmSettingDto alarmSettingDto = new AlarmSettingDto("test", "token");

        given(alarmSettingRepository.existsAlarmSettingByCalendarId(anyString())).willReturn(false);
        given(alarmSettingRepository.save(any(AlarmSetting.class))).willReturn(AlarmSettingDto.toEntity(alarmSettingDto));

        alarmSettingService.saveAlarmSetting(alarmSettingDto);

        then(alarmSettingRepository).should().existsAlarmSettingByCalendarId(anyString());
        then(alarmSettingRepository).should().save(any(AlarmSetting.class));
    }

    @DisplayName("기설정된 알람 설정 저장 테스트")
    @Test
    void nameSaveAlreadyAlarmSetting() {
        AlarmSettingDto alarmSettingDto = new AlarmSettingDto("test", "token");

        given(alarmSettingRepository.existsAlarmSettingByCalendarId(anyString())).willReturn(true);

        alarmSettingService.saveAlarmSetting(alarmSettingDto);

        then(alarmSettingRepository).should().existsAlarmSettingByCalendarId(anyString());
        then(alarmSettingRepository).shouldHaveNoMoreInteractions();
    }

    @DisplayName("설정된 알람 설정 제거 테스트")
    @Test
    void testDeleteAlarmSetting() {
        String calendarId = "test";

        willDoNothing().given(alarmSettingRepository).deleteAlarmSettingByCalendarId(anyString());

        alarmSettingService.deleteAlarmSetting(calendarId);

        then(alarmSettingRepository).should().deleteAlarmSettingByCalendarId(anyString());
    }
}