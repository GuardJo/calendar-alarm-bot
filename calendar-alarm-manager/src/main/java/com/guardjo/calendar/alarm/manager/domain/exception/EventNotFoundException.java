package com.guardjo.calendar.alarm.manager.domain.exception;

public class EventNotFoundException extends RuntimeException{
    public EventNotFoundException(String s) {
        super("Not Found Calendar Events, request calendarId = " + s);
    }
}
