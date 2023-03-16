package com.guardjo.calendar.alarm.manager.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * google api 호출 간 필요한 access token을 받아 전달하기 위한 컴포넌트
 * webclient 및 SecuriyConfig에서 설정할 경우 spring-web과 spring-webflux 충돌 발생 하기에 별도 구현
 * -> 둘 중 하나만 쓰는게 적절하긴 한듯
 */
@Component
public class AccessTokenGenerator {
    public final static String ACCESS_TOKEN_PREFIX = "Bearer";
    public final static String CLIENT_REGISTRATION_ID = "google";
    @Autowired
    private OAuth2AuthorizedClientService auth2AuthorizedClientService;
    private OAuth2AuthorizedClient client;

    public void setAutoriezedClient() {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String userName = token.getName();
        client = auth2AuthorizedClientService.loadAuthorizedClient(CLIENT_REGISTRATION_ID, userName);
    }

    public String getAccessToken() {
        setAutoriezedClient();
        return ACCESS_TOKEN_PREFIX + " " + client.getAccessToken().getTokenValue();
    }
}
