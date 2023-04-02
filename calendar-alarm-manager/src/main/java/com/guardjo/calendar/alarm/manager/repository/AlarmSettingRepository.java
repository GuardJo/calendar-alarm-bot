package com.guardjo.calendar.alarm.manager.repository;

import com.guardjo.calendar.alarm.manager.domain.AlarmSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlarmSettingRepository extends JpaRepository<AlarmSetting, Long> {
    Optional<AlarmSetting> findAlarmSettingByCalendarId(String calendarId);

    boolean existsAlarmSettingByCalendarId(String calendarId);

    void deleteAlarmSettingByCalendarId(String calendarId);
}
