package com.guardjo.calendar.alarm.manager.util;

import com.guardjo.calendar.alarm.manager.domain.*;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class TestDataGenerator {
    public static GoogleAccountInfo generateAccountInfo() {
        return GoogleAccountInfo.of("id", "email", "tester", true);
    }

    public static GoogleCalendarScheduleInfo generateGoogleCalendarScheduleInfo() {
        return GoogleCalendarScheduleInfo.of(
                new Date(),
                ZonedDateTime.now(),
                TimeZone.getDefault().getDisplayName()
        );
    }

    public static GoogleCalendarReminderInfo generateGoogleCalendarReminderInfo() {
        return GoogleCalendarReminderInfo.of(
                "method",
                1
        );
    }

    public static GoogleCalendarEventDto generateGoogleCalendarEventDto() {
        return GoogleCalendarEventDto.of(
                "kind",
                "etag",
                "testId",
                "test",
                "testLink",
                ZonedDateTime.now(),
                ZonedDateTime.now(),
                "test summary",
                "test description",
                "korea",
                "red",
                generateAccountInfo(),
                generateAccountInfo(),
                generateGoogleCalendarScheduleInfo(),
                generateGoogleCalendarScheduleInfo(),
                false,
                List.of(),
                "",
                generateGoogleCalendarScheduleInfo()
        );
    }

    public static GoogleCalendarEventResponse generateGoogleCalendarEventResponse() {
        return GoogleCalendarEventResponse.of(
                "kind",
                "etag",
                "test summary",
                "test description",
                ZonedDateTime.now(),
                TimeZone.getDefault().getDisplayName(),
                "TEST",
                List.of(generateGoogleCalendarReminderInfo()),
                "TestPageToken",
                "TestSyncToken",
                List.of(generateGoogleCalendarEventDto())
        );
    }
}
