package com.guardjo.calendar.alarm.manager.config;

import com.guardjo.calendar.alarm.manager.util.GoogleCalendarAPI;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((auth) ->
                        auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                .permitAll()
                                .mvcMatchers(HttpMethod.GET, "/")
                                .permitAll()
                                .anyRequest().authenticated()
                ).formLogin(withDefaults())
                .logout(logoutConfig -> logoutConfig.logoutSuccessUrl("/"))
                .oauth2Login()
                .and()
                .csrf().disable();

        return httpSecurity.build();
    }

    @Bean
    public WebClient webClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl(GoogleCalendarAPI.GOOGLE_CALENDER_REQUEST_PREFIX_URL)
                .build();

        return webClient;
    }
}
