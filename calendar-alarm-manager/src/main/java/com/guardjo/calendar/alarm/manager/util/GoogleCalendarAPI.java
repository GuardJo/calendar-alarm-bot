package com.guardjo.calendar.alarm.manager.util;

public class GoogleCalendarAPI {
    public final static String GOOGLE_CALENDER_REQUEST_PREFIX_URL = "https://www.googleapis.com/calendar/v3";
    public final static String REQUEST_GET_CALENDAR_URL = "/calendars";
    public final static String REQUEST_GET_CALENDAR_SETTINGS = "/users/me/settings";
    public final static String REQUEST_WATCH_EVENT_URL = "/calendars/{calendarId}/events/watch";
    public final static String REQUEST_WATCH_STOP_URL = "/channels/stop";
}
