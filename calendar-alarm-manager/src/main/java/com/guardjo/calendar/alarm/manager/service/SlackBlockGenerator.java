package com.guardjo.calendar.alarm.manager.service;

import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarEventDto;
import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarEventResponse;
import com.guardjo.calendar.alarm.manager.domain.slack.*;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class SlackBlockGenerator {
    public WebHookBody generateBlock(GoogleCalendarEventResponse googleCalendarEventResponse) {
        List<SlackBlock> eventBlocks = new ArrayList<>();

        eventBlocks.add(makeHeader(googleCalendarEventResponse.getSummary()));

        googleCalendarEventResponse.getItems().stream()
                .forEach(eventDto -> {
                    MessageDivider divider = makeDivider();
                    eventBlocks.add(divider);
                    eventBlocks.add(makeEvent(eventDto));
                    eventBlocks.add(divider);
                });

        return WebHookBody.of(eventBlocks);
    }

    /*
    헤더 부분 생성
     */
    private MessageBlock makeHeader(String calendarName) {
        return MessageBlock.of(
                BlockType.HEADER.getValue(),
                MessageText.of(
                        BlockType.PLAIN_TEXT.getValue(),
                        "Today's " + calendarName
                )
        );
    }

    /*
    구분선 생성
     */
    private MessageDivider makeDivider() {
        return MessageDivider.of();
    }

    /*
    이벤트 섹션 생성
     */
    private MessageBlock makeEvent(GoogleCalendarEventDto eventDto) {
        return MessageBlock.of(
                BlockType.SECTION.getValue(),
                MessageText.of(BlockType.MARKDOWN.getValue(),
                        String.format("*<%s|%s>*\n%s", eventDto.getHtmlLink(), eventDto.getSummary(),
                                printTimes(eventDto.getStart().getDateTime(), eventDto.getEnd().getDateTime(), eventDto.getStart().getTimeZone()))
                )
        );
    }

    /*
    시간값 포메팅
     */
    private String printTimes(ZonedDateTime startTime, ZonedDateTime endTime, String timeZone) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String start = startTime.format(formatter.withZone(ZoneId.of(timeZone)));
        String end = endTime.format(formatter.withZone(ZoneId.of(timeZone)));

        return String.format("%s-%s", start, end);
    }
}
