package com.guardjo.calendar.alarm.manager.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({SecurityConfig.class, JacksonConfig.class})
public class TestConfig {
}
