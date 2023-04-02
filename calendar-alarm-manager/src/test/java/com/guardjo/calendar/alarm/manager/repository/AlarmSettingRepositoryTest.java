package com.guardjo.calendar.alarm.manager.repository;

import com.guardjo.calendar.alarm.manager.domain.AlarmSetting;
import com.guardjo.calendar.alarm.manager.domain.AlarmSettingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class AlarmSettingRepositoryTest {
    private final AlarmSettingRepository alarmSettingRepository;

    private long testId = 1L;

    @Autowired
    AlarmSettingRepositoryTest(AlarmSettingRepository alarmSettingRepository) {
        this.alarmSettingRepository = alarmSettingRepository;
    }

    @BeforeEach
    @Rollback(value = false)
    void setUp() {
        AlarmSetting alarmSetting = AlarmSetting.of(null, "test", "token");

        testId = alarmSettingRepository.saveAndFlush(alarmSetting).getId();
        System.out.println("Setup Data, id = " + testId);
    }

    @DisplayName("알람 설정 정보 저장 테스트")
    @Test
    void testSaveAlarmSetting() {
        String testId = "testId";
        String testToken = "testToken";

        AlarmSettingDto alarmSettingDto = new AlarmSettingDto(testId, testToken);

        AlarmSetting alarmSetting = AlarmSettingDto.toEntity(alarmSettingDto);

        AlarmSettingDto actualData = AlarmSettingDto.from(alarmSettingRepository.saveAndFlush(alarmSetting));

        assertThat(actualData).isEqualTo(alarmSettingDto);
    }

    @DisplayName("식별값을 통해 알람 설정 정보 반환 테스트")
    @Test
    void testFindByIdAlarmSetting() {
        AlarmSettingDto alarmSettingDto = AlarmSettingDto.from(alarmSettingRepository.findById(testId).orElseThrow());

        assertThat(alarmSettingDto.calendarId()).isEqualTo("test");
    }

    @DisplayName("저장된 모든 알람 설정 정보 반환 테스트")
    @Test
    void testFindAllAlarmSettings() {
        List<AlarmSetting> alarmSettings = alarmSettingRepository.findAll();

        assertThat(alarmSettings.size()).isEqualTo(1);
    }

    @DisplayName("특정 알림 설정 정보 변경 테스트")
    @Test
    void testUpdateAlarmSetting() {
        AlarmSetting alarmSetting = alarmSettingRepository.findById(testId).orElseThrow();
        String token = "updateToken";

        alarmSetting.setAccessToken(token);
        alarmSettingRepository.flush();

        AlarmSetting actual = alarmSettingRepository.findById(testId).orElseThrow();

        assertThat(actual.getAccessToken()).isEqualTo(token);
    }

    @DisplayName("특정 알림 설정 정보 삭제 테스트")
    @Test
    void testDeleteAlarmSetting() {
        alarmSettingRepository.deleteById(testId);

        assertThat(alarmSettingRepository.count()).isEqualTo(0);
    }
}