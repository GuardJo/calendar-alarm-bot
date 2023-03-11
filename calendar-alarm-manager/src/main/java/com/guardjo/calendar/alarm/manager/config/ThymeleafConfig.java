package com.guardjo.calendar.alarm.manager.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class ThymeleafConfig {
    @Bean
    public SpringResourceTemplateResolver springResourceTemplateResolver(
            SpringResourceTemplateResolver springResourceTemplateResolver, Thymeleaf3Properties properties) {
        springResourceTemplateResolver.setUseDecoupledLogic(properties.isDecoupledLogic());
        return springResourceTemplateResolver;
    }

    @Getter
    @AllArgsConstructor
    @ConstructorBinding
    @ConfigurationProperties("spring.thymeleaf3")
    public static class Thymeleaf3Properties {
        /**
         * Thymeleaf Decoupled Logic Option
         */
        private final boolean decoupledLogic;
    }

}
