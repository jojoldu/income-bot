package com.jojoldu.incomebot.batch.notifier.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jojoldu.incomebot.core.lecture.LectureType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@ToString
@Getter
public class TelegramMessage {
    private static final String TEXT_FORMAT = "[%s] %s가 %s만큼 %s 하였습니다.";

    @JsonProperty("chat_id")
    private Long chatId;
    private String text;

    @Builder
    public TelegramMessage(Long chatId, String text) {
        this.chatId = chatId;
        this.text = text;
    }

    @Builder(builderClassName = "ByLecture", builderMethodName = "byLecture")
    public TelegramMessage(Long chatId, long beforeScore, long currentScore, LectureType type, String goods) {
        long changeScore = currentScore - beforeScore;
        this.chatId = chatId;
        this.text = createText(changeScore, type, goods);
    }

    public static String createText (long changeScore, LectureType lectureType, String goods) {
        String increaseType = changeScore >= 0? "증가" : "감소";
        return String.format(TEXT_FORMAT, lectureType.getTitle(), goods, changeScore, increaseType);
    }
}
