package com.guardjo.calendar.alarm.manager.util;

import com.guardjo.calendar.alarm.manager.domain.*;

import java.util.List;

public class TestDataGenerator {
    public static GoogleCalendarDto generateGoogleCalendarDto() {
        GoogleCalendarDto googleCalendarDto = new GoogleCalendarDto();
        googleCalendarDto.setKind("calendar#calendar");
        googleCalendarDto.setEtag("test");
        googleCalendarDto.setId("test id");
        googleCalendarDto.setSummary("test");
        googleCalendarDto.setDescription("test");
        googleCalendarDto.setLocation("test");
        googleCalendarDto.setTimeZone("Asia/Seoul");
        googleCalendarDto.setConferenceProperties(generateConferenceProperties());

        return googleCalendarDto;
    }

    public static ConferenceProperties generateConferenceProperties() {
        ConferenceProperties conferenceProperties = new ConferenceProperties();
        conferenceProperties.setAllowedConferenceSolutionTypes(List.of("hangoutsMeet"));

        return conferenceProperties;
    }

    public static GoogleCalendarSettingDto generateGoogleCalendarSettingDto() {
        GoogleCalendarSettingDto googleCalendarSettingDto = new GoogleCalendarSettingDto();
        googleCalendarSettingDto.setId("test id");
        googleCalendarSettingDto.setEtag("test");
        googleCalendarSettingDto.setKind("calendar#setting");
        googleCalendarSettingDto.setValue("test");

        return googleCalendarSettingDto;
    }

    public static GoogleCalendarSettingsDto generateGoogleCalendarSettingsDto() {
        GoogleCalendarSettingsDto googleCalendarSettingsDto = new GoogleCalendarSettingsDto();
        googleCalendarSettingsDto.setEtag("test");
        googleCalendarSettingsDto.setKind("calendar#settings");
        googleCalendarSettingsDto.setItems(List.of(generateGoogleCalendarSettingDto()));
        googleCalendarSettingsDto.setNextPageToken("test");
        googleCalendarSettingsDto.setNextSyncToken("test");

        return googleCalendarSettingsDto;
    }

    public static WatchRequest generateWatchRequest() {
        WatchRequest watchRequest = new WatchRequest();
        watchRequest.setId("test");
        watchRequest.setType("web_hook");
        watchRequest.setAddress("test addr");

        return watchRequest;
    }

    public static WatchResponse generateWatchResponse() {
        WatchResponse watchResponse = new WatchResponse();
        watchResponse.setId("test");
        watchResponse.setToken("test token");
        watchResponse.setExpiration(604800000);
        watchResponse.setResourceUri("test uri");
        watchResponse.setResourceId("test id");

        return watchResponse;
    }
}
