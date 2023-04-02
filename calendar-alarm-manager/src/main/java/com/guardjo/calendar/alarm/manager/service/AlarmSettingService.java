package com.guardjo.calendar.alarm.manager.service;

import com.guardjo.calendar.alarm.manager.domain.AlarmSettingDto;
import com.guardjo.calendar.alarm.manager.repository.AlarmSettingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class AlarmSettingService {
    private final AlarmSettingRepository alarmSettingRepository;

    public AlarmSettingService(AlarmSettingRepository alarmSettingRepository) {
        this.alarmSettingRepository = alarmSettingRepository;
    }

    public void saveAlarmSetting(AlarmSettingDto alarmSettingDto) {
        if (isExistAlarmSetting(alarmSettingDto.calendarId())) {
            log.info("[Test] Already Setting Alarm, calendarId = {}", alarmSettingDto.calendarId());
            return;
        }

        alarmSettingRepository.save(AlarmSettingDto.toEntity(alarmSettingDto));
        log.info("[Test] Save new AlarmSetting, calendarId = {}", alarmSettingDto.calendarId());
    }

    public void deleteAlarmSetting(String calendarId) {
        alarmSettingRepository.deleteAlarmSettingByCalendarId(calendarId);
        log.info("[Test] Deleted AlarmSetting, calendarId = {}", calendarId);
    }

    /*
    이미 해당 calendarId로 저장된 설정이 있는지 여부 반환
     */
    private boolean isExistAlarmSetting(String calendarId) {
        return alarmSettingRepository.existsAlarmSettingByCalendarId(calendarId);
    }
}
