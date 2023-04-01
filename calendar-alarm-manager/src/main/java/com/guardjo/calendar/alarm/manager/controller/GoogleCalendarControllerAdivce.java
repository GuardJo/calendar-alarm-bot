package com.guardjo.calendar.alarm.manager.controller;

import com.guardjo.calendar.alarm.manager.domain.exception.EventNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GoogleCalendarControllerAdivce {
    @ExceptionHandler(value = EventNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(EventNotFoundException e) {
        log.warn("[Test] {}", e.getMessage());

        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
