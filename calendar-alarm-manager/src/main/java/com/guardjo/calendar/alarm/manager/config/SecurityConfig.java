package com.guardjo.calendar.alarm.manager.config;

import com.guardjo.calendar.alarm.manager.util.GoogleCalendarAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
    @Value("${slack.webhook.url}")
    private String slackWebhookUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((auth) ->
                        auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                .permitAll()
                                .mvcMatchers(HttpMethod.GET, "/")
                                .permitAll()
                                .mvcMatchers(HttpMethod.POST, "/web-hook/receive")
                                .permitAll()
                                .antMatchers(HttpMethod.GET, "/h2-console/**")
                                .permitAll()
                                .anyRequest().authenticated()
                                .and()
                ).formLogin(withDefaults())
                .logout(logoutConfig -> logoutConfig.logoutSuccessUrl("/"))
                .oauth2Login()
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable();

        return httpSecurity.build();
    }

    @Bean
    public WebClient webClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl(GoogleCalendarAPI.GOOGLE_CALENDER_REQUEST_PREFIX_URL)
                .filter(((request, next) -> {
                    log.info("[Test] RequestUrl = {}", request.url());
                    log.info("[Test] RequestHeaders = {}", request.headers());
                    return next.exchange(request);
                }))
                .build();

        return webClient;
    }

    @Bean
    public WebClient slackClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl(slackWebhookUrl)
                .filter(((request, next) -> {
                    log.info("[Test] RequestUrl = {}", request.url());
                    log.info("[Test] RequestHeaders = {}", request.headers());
                    return next.exchange(request);
                }))
                .build();

        return webClient;
    }
}
