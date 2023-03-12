package com.guardjo.calendar.alarm.manager.domain;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConferenceProperties {
    private List<String> allowedConferenceSolutionTypes;
}
