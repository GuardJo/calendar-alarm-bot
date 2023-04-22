package com.guardjo.calendar.alarm.manager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class ThymeleafConfig {

    @Bean
    public SpringResourceTemplateResolver springResourceTemplateResolver(
            SpringResourceTemplateResolver springResourceTemplateResolver, Thymeleaf3Properties properties) {
        springResourceTemplateResolver.setUseDecoupledLogic(properties.decoupledLogic());
        return springResourceTemplateResolver;
    }

    @ConfigurationProperties("spring.thymeleaf3")
    public static record Thymeleaf3Properties(boolean decoupledLogic) {
    }
}
