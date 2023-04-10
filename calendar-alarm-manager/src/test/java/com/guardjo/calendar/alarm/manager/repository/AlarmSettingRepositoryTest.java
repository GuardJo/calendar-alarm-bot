package com.guardjo.calendar.alarm.manager.repository;

import com.guardjo.calendar.alarm.manager.domain.AlarmSetting;
import com.guardjo.calendar.alarm.manager.domain.AlarmSettingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

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

    @DisplayName("주어진 calendarId를 지닌 알림 설정 정보 반환 테스트")
    @Test
    void testFindByCalendarId() {
        String calendarId = "test";
        Optional<AlarmSetting> alarmSetting = alarmSettingRepository.findAlarmSettingByCalendarId(calendarId);

        assertThat(alarmSetting.isEmpty()).isFalse();
        assertThat(alarmSetting.get().getCalendarId()).isEqualTo(calendarId);
    }

    @DisplayName("해당 calendarId로 지정된 알림 설정 정보 존재 여부 반환 테스트")
    @ParameterizedTest
    @MethodSource("getCalendarIdListAndResult")
    void testExistByCalendarId(String calendarId, boolean expected) {
        boolean isExist = alarmSettingRepository.existsAlarmSettingByCalendarId(calendarId);

        assertThat(isExist).isEqualTo(expected);
    }

    @DisplayName("해당하는 calendarId로 지정된 알림 설정 제거 테스트")
    @Test
    void testDeleteByCalendarId() {
        String calendarId = "test";

        assertThatCode(() -> alarmSettingRepository.deleteAlarmSettingByCalendarId(calendarId)).doesNotThrowAnyException();
        assertThat(alarmSettingRepository.count()).isEqualTo(0L);
    }

    public static Stream<Arguments> getCalendarIdListAndResult() {
        return Stream.of(
                Arguments.arguments("test", true),
                Arguments.arguments("test2", false)
        );
    }
}