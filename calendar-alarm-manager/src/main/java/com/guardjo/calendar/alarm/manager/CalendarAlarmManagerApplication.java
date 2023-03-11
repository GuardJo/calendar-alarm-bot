package com.guardjo.calendar.alarm.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class CalendarAlarmManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalendarAlarmManagerApplication.class, args);
    }

}
