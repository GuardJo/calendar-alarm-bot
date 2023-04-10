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

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

    @DisplayName("저장된 알람설정 목록 반환 테스트")
    @Test
    void testFindAllAlarmSettings() {
        List<AlarmSetting> expected = List.of(AlarmSetting.of(-1L, "testId", "testToken"));

        given(alarmSettingRepository.findAll()).willReturn(expected);

        List<AlarmSetting> actual = alarmSettingService.findAll();

        assertThat(actual).isEqualTo(expected);
        then(alarmSettingRepository).should().findAll();
    }

    @DisplayName("알림설정으로 지정된 calendarId 목록 반환 테스트")
    @Test
    void testFindAllSettingCalendarIds() {
        String expectedId = "testId";
        Set<String> expected = Set.of(expectedId);
        List<AlarmSetting> alarmSettings = List.of(AlarmSetting.of(-1L, expectedId, "testToken"));

        given(alarmSettingRepository.findAll()).willReturn(alarmSettings);

        Set<String> actual = alarmSettingService.findAllSettingCalendarIds();

        assertThat(actual).isEqualTo(expected);
        then(alarmSettingRepository).should().findAll();
    }

    @DisplayName("저장된 알림 설정 정보가 없을 때 목록 반환 테스트")
    @Test
    void testFindAllSettingButNull() {
        List<AlarmSetting> expected = List.of();

        given(alarmSettingRepository.findAll()).willReturn(expected);

        List<AlarmSetting> actual = alarmSettingService.findAll();

        assertThat(actual.isEmpty()).isTrue();
        then(alarmSettingRepository).should().findAll();
    }

    @DisplayName("저장된게 없을 때 알림설정의 calendarId 목록 반환")
    @Test
    void testFindAllSettingsCalendarIdButNull() {
        given(alarmSettingRepository.findAll()).willReturn(List.of());

        Set<String> actual = alarmSettingService.findAllSettingCalendarIds();

        assertThat(actual.isEmpty()).isTrue();
        then(alarmSettingRepository).should().findAll();
    }
}