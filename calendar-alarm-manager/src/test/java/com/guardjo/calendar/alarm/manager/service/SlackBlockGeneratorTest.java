package com.guardjo.calendar.alarm.manager.service;

import com.guardjo.calendar.alarm.manager.domain.GoogleCalendarEventResponse;
import com.guardjo.calendar.alarm.manager.domain.slack.BlockType;
import com.guardjo.calendar.alarm.manager.domain.slack.MessageText;
import com.guardjo.calendar.alarm.manager.domain.slack.WebHookBody;
import com.guardjo.calendar.alarm.manager.util.TestDataGenerator;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class SlackBlockGeneratorTest {
    @InjectMocks
    private SlackBlockGenerator slackBlockGenerator;

    @DisplayName("슬랙 메시지 블럭 생성 테스트")
    @Test
    void testGenerateBlock() {
        GoogleCalendarEventResponse testResponse = TestDataGenerator.generateGoogleCalendarEventResponse();

        WebHookBody actual = slackBlockGenerator.generateBlock(testResponse);

        assertThat(actual)
                .extracting("blocks", InstanceOfAssertFactories.COLLECTION)
                .anySatisfy((block) -> {
                    assertThat(block)
                            .hasFieldOrPropertyWithValue("type", BlockType.HEADER.getValue())
                            .extracting("text", InstanceOfAssertFactories.type(MessageText.class))
                            .hasFieldOrPropertyWithValue("type", BlockType.PLAIN_TEXT.getValue())
                            .hasFieldOrPropertyWithValue("text", "Today's " + testResponse.getSummary());
                })
                .anySatisfy((block) -> {
                    assertThat(block)
                            .hasFieldOrPropertyWithValue("type", BlockType.DIVIDER.getValue());
                })
                .anySatisfy((block) -> {
                    assertThat(block)
                            .hasFieldOrPropertyWithValue("type", BlockType.SECTION.getValue())
                            .extracting("text", InstanceOfAssertFactories.type(MessageText.class))
                            .hasFieldOrPropertyWithValue("type", BlockType.MARKDOWN.getValue())
                            .hasFieldOrProperty("text");
                });
    }
}